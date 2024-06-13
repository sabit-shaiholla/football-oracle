import React, { useState, useEffect } from 'react';
import {
    Box,
    Button,
    Flex,
    Heading,
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    Text,
    useColorModeValue,
    Textarea,
    NumberInput,
    NumberInputField,
} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import { getReviewsByUser, updateReview, deleteReview } from './services/client.js';
import {successNotification, errorNotification} from "./services/notification.js";

const Home = () => {
    const [reviews, setReviews] = useState([]);
    const [selectedReview, setSelectedReview] = useState(null);
    const [updatedReview, setUpdatedReview] = useState('');
    const [updatedRating, setUpdatedRating] = useState('');

    useEffect(() => {
        fetchReviews();
    }, []);

    const fetchReviews = async () => {
        try {
            const response = await getReviewsByUser();
            setReviews(response.data);
        } catch (e) {
            console.error(e);
            errorNotification('Error', 'Failed to fetch reviews. Please try again.');
        }
    };

    const handleUpdateReview = async () => {
        if (!selectedReview) return;

        if (updatedReview.trim() === '' || updatedRating === '') {
            errorNotification('Validation Error', 'Review and rating fields cannot be empty');
            return;
        }

        const ratingValue = parseInt(updatedRating, 10);
        if (isNaN(ratingValue) || ratingValue < 1 || ratingValue > 10) {
            errorNotification('Validation Error', 'Please provide a rating between 1 and 10');
            return;
        }

        if (updatedReview === selectedReview.review && String(ratingValue) === selectedReview.rating) {
            errorNotification('Validation Error', 'No data is changed to update');
            return;
        }

        try {
            const reviewRequest = {
                playerId: selectedReview.playerId,
                review: updatedReview,
                rating: ratingValue,
            };
            await updateReview(selectedReview.reviewId, reviewRequest);
            successNotification('Success', 'Review updated successfully');
            fetchReviews();
            setSelectedReview(null);
            setUpdatedReview('');
            setUpdatedRating('');
        } catch (e) {
            console.error(e);
            errorNotification('Error', 'Failed to update review. Please try again.');
        }
    };

    const handleDeleteReview = async (reviewId) => {
        try {
            await deleteReview(reviewId);
            successNotification('Success', 'Review deleted successfully');
            fetchReviews();
        } catch (e) {
            console.error(e);
            errorNotification('Error', 'Failed to delete review. Please try again.');
        }
    };

    const handleReviewSelection = (review) => {
        setSelectedReview(review);
        setUpdatedReview(review.review);
        setUpdatedRating(review.rating);
    };
    const tableBg = useColorModeValue('white', 'gray.800');
    const tableBorder = useColorModeValue('gray.200', 'gray.700');

    return (
        <SidebarWithHeader>
            <Text fontSize={'6xl'} mb={6}>Dashboard</Text>
            <Box bg={tableBg} border="1px" borderColor={tableBorder} borderRadius="md" p={4}>
                <Heading as="h2" size="md" mb={4}>Your Reviews</Heading>
                <Table variant="striped" colorScheme="teal">
                    <Thead>
                        <Tr>
                            <Th>Username</Th>
                            <Th>Player Name</Th>
                            <Th>Review</Th>
                            <Th>Rating</Th>
                            <Th>Actions</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {reviews.map((review) => (
                            <Tr key={review.reviewId}>
                                <Td>{review.username}</Td>
                                <Td>{review.playerName}</Td>
                                <Td>{review.review}</Td>
                                <Td>{review.rating}</Td>
                                <Td>
                                    <Button size="sm" colorScheme="blue" mr={2} onClick={() => handleReviewSelection(review)}>Update</Button>
                                    <Button size="sm" colorScheme="red" onClick={() => handleDeleteReview(review.reviewId)}>Delete</Button>
                                </Td>
                            </Tr>
                        ))}
                    </Tbody>
                </Table>
            </Box>

            {selectedReview && (
                <Box mt={6} bg={tableBg} border="1px" borderColor={tableBorder} borderRadius="md" p={4}>
                    <Heading as="h3" size="md" mb={4}>Update Review for {selectedReview.playerName}</Heading>
                    <Textarea
                        placeholder="Update your review here..."
                        value={updatedReview}
                        onChange={(e) => setUpdatedReview(e.target.value)}
                        mb={4}
                    />
                    <NumberInput
                        max={10}
                        min={1}
                        value={updatedRating === '' ? '' : parseInt(updatedRating, 10)} // Handle empty string case
                        onChange={(value) => setUpdatedRating(value)}
                    >
                        <NumberInputField />
                    </NumberInput>
                    <Flex mt={4} justify="space-between">
                        <Button colorScheme="blue" onClick={handleUpdateReview}>Update Review</Button>
                        <Button colorScheme="red" onClick={() => setSelectedReview(null)}>Cancel</Button>
                    </Flex>
                </Box>
            )}
        </SidebarWithHeader>
    );
};

export default Home;