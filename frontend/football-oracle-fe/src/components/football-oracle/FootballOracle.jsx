import React, { useState } from 'react';
import { Box, Button, Grid, Input, Text, Table, Thead, Tbody, Tr, Th, Td } from '@chakra-ui/react';
import { getPlayer, getPlayerReport } from '../../services/client.js';
import SidebarWithHeader from '../shared/SideBar.jsx';

const FootballOracle = () => {
    const [playerName, setPlayerName] = useState('');
    const [playerData, setPlayerData] = useState(null);
    const [playerReportData, setPlayerReportData] = useState(null);

    const fetchPlayerData = async () => {
        try {
            const player = await getPlayer(playerName);
            const playerReport = await getPlayerReport(playerName);
            setPlayerData(player.data);
            setPlayerReportData(playerReport.data);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <SidebarWithHeader>
            <Box p={5}>
                <Text fontSize="2xl" mb={5}>Football Oracle</Text>
                <Input
                    placeholder="Enter player name"
                    mb={5}
                    value={playerName}
                    onChange={(e) => setPlayerName(e.target.value)}
                />
                <Button onClick={fetchPlayerData}>Fetch Player Data</Button>
                <Grid templateColumns="repeat(2, 1fr)" gap={6} mt={5}>
                    <Box w="100%" h="10" bg="blue.500">
                        <Text fontSize="xl">Player Data</Text>
                        <Table variant="simple">
                            <Thead>
                                <Tr>
                                    <Th>Key</Th>
                                    <Th>Value</Th>
                                </Tr>
                            </Thead>
                            <Tbody>
                                {playerData && Object.entries(playerData).map(([key, value], index) => (
                                    <Tr key={index}>
                                        <Td>{key}</Td>
                                        <Td>{JSON.stringify(value)}</Td>
                                    </Tr>
                                ))}
                            </Tbody>
                        </Table>
                    </Box>
                    <Box w="100%" h="10" bg="blue.500">
                        <Text fontSize="xl">Player Report Data</Text>
                        <Table variant="simple">
                            <Thead>
                                <Tr>
                                    <Th>Key</Th>
                                    <Th>Value</Th>
                                </Tr>
                            </Thead>
                            <Tbody>
                                {playerReportData && Object.entries(playerReportData).map(([key, value], index) => (
                                    <Tr key={index}>
                                        <Td>{key}</Td>
                                        <Td>{JSON.stringify(value)}</Td>
                                    </Tr>
                                ))}
                            </Tbody>
                        </Table>
                    </Box>
                </Grid>
            </Box>
        </SidebarWithHeader>
    );
};

export default FootballOracle;