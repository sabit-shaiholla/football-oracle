import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getUsers = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const saveUser = async (customer) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users`,
            customer
        )
    } catch (e) {
        throw e;
    }
}

export const updateUser = async (id, update) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}`,
            update,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const deleteUser = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const login = async (usernameAndPassword) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndPassword
        )
    } catch (e) {
        throw e;
    }
}

export const getPlayer = async (playerName) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/players/${playerName}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getPlayerReport = async (playerName) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/player-report?playerName=${playerName}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const createReview = async (reviewRequest) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/reviews`,
            reviewRequest,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const updateReview = async (reviewId, reviewRequest) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/reviews/${reviewId}`,
            reviewRequest,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getReviewsByUser = async() => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/reviews`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getPlayerReviewByUser = async(playerId) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/reviews/player/${playerId}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const deleteReview = async (reviewId) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/reviews/${reviewId}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}