import { api, handleApiError } from './request.config';
import { AxiosError } from 'axios';
import { CreatePixRequestTypes, PixResponseTypes } from '@/types/requests.types';

export const pixService = {
  async createPix(data: CreatePixRequestTypes): Promise<PixResponseTypes> {
    try {
      const response = await api.post<PixResponseTypes>('/api/pix', data);
      return response.data;
    } catch (error) {
      if (error instanceof AxiosError) return handleApiError(error);
      throw error;
    }
  },
};
