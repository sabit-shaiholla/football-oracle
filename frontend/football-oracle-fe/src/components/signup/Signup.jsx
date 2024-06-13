import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Flex, Heading, Image, Link, Stack, Text, useColorModeValue} from "@chakra-ui/react";
import CreateUserForm from "../shared/CreateUserForm.jsx";

const Signup = () => {
    const { customer: user, setCustomerFromToken: setUserFromToken } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            navigate("/dashboard/users");
        }
    }, [user, navigate]);

    const rightBgColor = useColorModeValue('gray.600', 'gray.900');

    return (
        <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={"https://raw.githubusercontent.com/sabit-shaiholla/football-oracle/main/.github/images/logo.jpg"}
                        boxSize={"200px"}
                        alt={"Football Oracle Logo"}
                        alignSelf={"center"}
                    />
                    <Heading fontSize={'2xl'} mb={15}>Register for an account</Heading>
                    <CreateUserForm onSuccess={(token) => {
                        localStorage.setItem("access_token", token)
                        setUserFromToken()
                        navigate("/dashboard");
                    }} />
                    <Link color={"blue.500"} href={"/"}>
                        Have an account? Login now.
                    </Link>
                </Stack>
            </Flex>
            <Flex
                flex={1}
                p={10}
                flexDirection={"column"}
                alignItems={"center"}
                justifyContent={"center"}
                bg={rightBgColor}
            >
                <Text fontSize={"4xl"} color={'white'} fontWeight={"bold"} mb={5}>
                    <Link target={"_blank"} href={"https://github.com/sabit-shaiholla/football-oracle"}>
                        Get more information
                    </Link>
                </Text>
                <Image
                    alt={'Signup Image'}
                    objectFit={'scale-down'}
                    src={
                        'https://raw.githubusercontent.com/sabit-shaiholla/football-oracle/main/.github/images/signup-logo.jpg'
                    }
                />
            </Flex>
        </Stack>
    );
}

export default Signup;