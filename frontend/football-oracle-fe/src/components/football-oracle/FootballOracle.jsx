import React, { useState } from 'react';
import {
    Box,
    Button,
    Flex,
    Heading,
    Grid,
    GridItem,
    Input,
    Text,
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    useColorModeValue, NumberInputField, NumberInput, Textarea
} from '@chakra-ui/react';
import { getPlayer, getPlayerReport, createReview, updateReview, deleteReview, getPlayerReviewByUser } from '../../services/client.js';
import { successNotification, errorNotification } from "../../services/notification.js";
import SidebarWithHeader from '../shared/SideBar.jsx';

const FootballOracle = () => {
    const [playerName, setPlayerName] = useState('');
    const [playerData, setPlayerData] = useState(null);
    const [playerReport, setPlayerReport] = useState(null);
    const [review, setReview] = useState('');
    const [rating, setRating] = useState('');
    const [originalReview, setOriginalReview] = useState('');
    const [originalRating, setOriginalRating] = useState('');
    const [reviewId, setReviewId] = useState(null);
    const [error, setError] = useState('');

    const handleInputChange = (e) => {
        setPlayerName(e.target.value);
    };

    const handleGetPlayerReport = async () => {
        try {
            const playerReportResponse = await getPlayerReport(playerName);
            setPlayerReport(playerReportResponse.data);

            const playerResponse = await getPlayer(playerName);
            setPlayerData(playerResponse.data);

            try {
                const playerReviewResponse = await getPlayerReviewByUser(playerResponse.data.playerId);
                if (playerReviewResponse.data) {
                    setReview(playerReviewResponse.data.review);
                    setRating(String(playerReviewResponse.data.rating));
                    setOriginalReview(playerReviewResponse.data.review);
                    setOriginalRating(String(playerReviewResponse.data.rating));
                    setReviewId(playerReviewResponse.data.reviewId);
                } else {
                    setReview('');
                    setRating('');
                    setOriginalReview('');
                    setOriginalRating('');
                    setReviewId(null);
                }
            } catch (reviewError) {
                if (reviewError.response && reviewError.response.status === 404) {
                    setReview('');
                    setRating('');
                    setOriginalReview('');
                    setOriginalRating('');
                    setReviewId(null);
                } else {
                    throw reviewError;
                }
            }

            setError('');
        } catch (e) {
            setError('Failed to fetch player data. Please try again.');
            setPlayerData(null);
            setPlayerReport(null);
            setReview('');
            setRating('');
            setOriginalReview('');
            setOriginalRating('');
            setReviewId(null);
        }
    };

    const handleReviewChange = (e) => {
        setReview(e.target.value);
    };

    const handleRatingChange = (value) => {
        setRating(String(value));
    };

    const handleCreateReview = async () => {
        if (review.trim() === '' || rating === '') {
            errorNotification('Validation Error', 'Review and rating fields cannot be empty');
            return;
        }
        const ratingValue = parseInt(rating, 10);
        if (isNaN(ratingValue) || ratingValue < 1 || ratingValue > 10) {
            errorNotification('Validation Error', 'Please provide a rating between 1 and 10');
            return;
        }
        try {
            const reviewRequest = {
                playerId: playerData.playerId,
                review: review,
                rating: ratingValue,
            };
            const response = await createReview(reviewRequest);
            setReviewId(response.data.reviewId);
            setOriginalReview(review);
            setOriginalRating(String(ratingValue)); // Store rating as string
            successNotification('Success', 'Review created successfully');
        } catch (e) {
            errorNotification('Error', 'Failed to create review. Please try again.');
        }
    };

    const handleUpdateReview = async () => {
        if (review.trim() === '' || rating === '') {
            errorNotification('Validation Error', 'Review and rating fields cannot be empty');
            return;
        }
        const ratingValue = parseInt(rating, 10);
        if (isNaN(ratingValue) || ratingValue < 1 || ratingValue > 10) {
            errorNotification('Validation Error', 'Please provide a rating between 1 and 10');
            return;
        }
        if (review === originalReview && String(ratingValue) === originalRating) {
            errorNotification('Validation Error', 'No data is changed to update');
            return;
        }
        if (!reviewId) {
            errorNotification('Error', 'No review to update');
            return;
        }
        try {
            const reviewRequest = {
                playerId: playerData.playerId,
                review: review,
                rating: ratingValue,
            };
            await updateReview(reviewId, reviewRequest);
            setOriginalReview(review);
            setOriginalRating(String(ratingValue)); // Store rating as string
            successNotification('Success', 'Review updated successfully');
        } catch (e) {
            errorNotification('Error', 'Failed to update review. Please try again.');
        }
    };

    const handleDeleteReview = async () => {
        if (reviewId) {
            try {
                await deleteReview(reviewId);
                setReview('');
                setRating('');
                setOriginalReview('');
                setOriginalRating('');
                setReviewId(null);
                successNotification('Success', 'Review deleted successfully');
            } catch (e) {
                errorNotification('Error', 'Failed to delete review. Please try again.');
            }
        } else {
            errorNotification('Error', 'No review to delete');
        }
    };

    const tableBg = useColorModeValue('white', 'gray.800');
    const tableBorder = useColorModeValue('gray.200', 'gray.700');

    const formatText = (text) => {
        return text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
    };

    return (
        <SidebarWithHeader>
            <Box p={6}>
                <Heading as="h1" mb={6}>
                    Football Oracle
                </Heading>
                <Flex mb={6}>
                    <Input
                        placeholder="Kylian Mbappe"
                        value={playerName}
                        onChange={handleInputChange}
                        mr={4}
                    />
                    <Button colorScheme="blue" onClick={handleGetPlayerReport}>
                        Get Player Report
                    </Button>
                </Flex>
                {error && <Text color="red.500" mb={4}>{error}</Text>}
                <Grid templateColumns="repeat(2, 1fr)" gap={6}>
                    {playerData && (
                        <GridItem>
                            <Box bg={tableBg} border="1px" borderColor={tableBorder} borderRadius="md" p={4}>
                                <Table variant="striped" colorScheme="teal">
                                    <Thead>
                                        <Tr>
                                            <Th>Statistic</Th>
                                            <Th>Value</Th>
                                        </Tr>
                                    </Thead>
                                    <Tbody>
                                        {Object.entries(playerData.statistics).map(([key, value]) => (
                                            <Tr key={key}>
                                                <Td>{key}</Td>
                                                <Td>{value}</Td>
                                            </Tr>
                                        ))}
                                    </Tbody>
                                </Table>
                            </Box>
                        </GridItem>
                    )}
                    {playerReport && (
                        <GridItem>
                            <Box bg={tableBg} border="1px" borderColor={tableBorder} borderRadius="md" p={4}>
                                <Heading as="h2" size="md" mb={4}>{playerReport.playerName}</Heading>
                                <Box mb={4}>
                                    <Heading as="h3" size="sm">Player Information</Heading>
                                    {playerData && (
                                        <>
                                            <Text>Position: {playerData.playerPosition}</Text>
                                            <Text>Age: {playerData.playerAge}</Text>
                                            <Text>Birthday: {playerData.birthday}</Text>
                                            <Text>Team: {playerData.team}</Text>
                                        </>
                                    )}
                                </Box>
                                <Box mb={4}>
                                    <Heading as="h3" size="sm">Strengths</Heading>
                                    <Text whiteSpace="pre-wrap" dangerouslySetInnerHTML={{ __html: formatText(playerReport.playerStrengths) }} />
                                </Box>
                                <Box mb={4}>
                                    <Heading as="h3" size="sm">Weaknesses</Heading>
                                    <Text whiteSpace="pre-wrap" dangerouslySetInnerHTML={{ __html: formatText(playerReport.playerWeaknesses) }} />
                                </Box>
                                <Box>
                                    <Heading as="h3" size="sm">Summary</Heading>
                                    <Text whiteSpace="pre-wrap">{playerReport.playerSummary}</Text>
                                </Box>
                                <Box mt={6}>
                                    <Heading as="h3" size="sm" mb={2}>Review</Heading>
                                    <Textarea
                                        placeholder="Write your review here..."
                                        value={review}
                                        onChange={handleReviewChange}
                                    />
                                </Box>
                                <Box mt={4}>
                                    <Heading as="h3" size="sm" mb={2}>Rating</Heading>
                                    <NumberInput
                                        max={10}
                                        min={1}
                                        value={parseInt(rating, 10)} // Convert to number for NumberInput
                                        onChange={handleRatingChange}
                                    >
                                        <NumberInputField />
                                    </NumberInput>
                                </Box>
                                <Flex mt={4} justify="space-between">
                                    <Button colorScheme="green" onClick={handleCreateReview}>Create Review</Button>
                                    <Button colorScheme="blue" onClick={handleUpdateReview}>Update Review</Button>
                                    <Button colorScheme="red" onClick={handleDeleteReview}>Delete Review</Button>
                                </Flex>
                            </Box>
                        </GridItem>
                    )}
                </Grid>
            </Box>
        </SidebarWithHeader>
    );
};

export default FootballOracle;