import axios, { AxiosError } from 'axios';
import { ApiErrorResponseTypes } from '@/types/requests.types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL;

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const handleApiError = (error: AxiosError<ApiErrorResponseTypes>): never => {
  const errorMessage = error.response?.data?.message || 'Ocorreu um erro na requisição';
  throw new Error(errorMessage);
};
