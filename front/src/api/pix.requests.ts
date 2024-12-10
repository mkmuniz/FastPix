import { api, handleApiError } from './request.config';
import { AxiosError } from 'axios';

interface CreatePixRequest {
  value: number;
  name: string;
  pixKey: string;
  state: string;
  city: string;
}

interface PixResponse {
  id: string;
  value: number;
  name: string;
  status: string;
  createdAt: string;
  qrCodeText: string;
  qrCodeImage: string;
}

export const pixService = {
  async createPix(data: CreatePixRequest): Promise<PixResponse> {
    try {
      const response = await api.post<PixResponse>('/api/pix', data);
      return response.data;
    } catch (error) {
      if (error instanceof AxiosError) {
        return handleApiError(error);
      }
      throw error;
    }
  },
};
