import axios from 'axios';
import { API_URL } from '../config';

export const findAll = async () => {
    const response = await axios.get(API_URL);
    return response;
};

export const create = async ({ name, description, price }) => {
    const response = await axios.post(API_URL, { name, description, price });
    return response;
};

export const update = async ({ id, name, description, price }) => {
    const response = await axios.put(`${API_URL}/${id}`, { name, description, price });
    return response;
};

export const remove = async (id) => {
    await axios.delete(`${API_URL}/${id}`);
};
