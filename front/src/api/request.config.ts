import axios from 'axios';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const handleApiError = (error: any) => {
  const errorMessage = error.response?.data?.message || 'Ocorreu um erro na requisição';
  throw new Error(errorMessage);
};