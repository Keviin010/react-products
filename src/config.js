const baseUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const API_URL = `${baseUrl.replace(/\/$/, '')}/api/products`;

export const MAX_ATTEMPTS = baseUrl.includes('localhost') ? 1 : 15;
