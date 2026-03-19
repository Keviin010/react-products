import axios from "axios";

// Centralizo la URL base aquí para cambiarla fácilmente cuando conecte el backend
const BASE_URL = 'http://localhost:8080/api/products';

export const findAll = async () => {
    const response = await axios.get(BASE_URL);
    return response;
};

export const create = async ({ name, description, price }) => {
    const response = await axios.post(BASE_URL, { name, description, price });
    return response;
};

export const update = async ({ id, name, description, price }) => {
    const response = await axios.put(`${BASE_URL}/${id}`, { name, description, price });
    return response;
};

export const remove = async (id) => {
    await axios.delete(`${BASE_URL}/${id}`);
};