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
    useColorModeValue
} from '@chakra-ui/react';
import { getPlayer, getPlayerReport } from '../../services/client.js';
import SidebarWithHeader from '../shared/SideBar.jsx';

const FootballOracle = () => {
    const [playerName, setPlayerName] = useState('');
    const [playerData, setPlayerData] = useState(null);
    const [playerReport, setPlayerReport] = useState(null);
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

            setError('');
        } catch (e) {
            setError('Failed to fetch player data. Please try again.');
            setPlayerData(null);
            setPlayerReport(null);
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
                            </Box>
                        </GridItem>
                    )}
                </Grid>
            </Box>
        </SidebarWithHeader>
    );
};

export default FootballOracle;