import { api, handleApiError } from './request.config';
import { AxiosError } from 'axios';

interface PixCreateRequest {
  value: number;
  description?: string;
}

interface PixResponse {
  id: string;
  value: number;
  description?: string;
  status: string;
  createdAt: string;
}

interface QRCodeResponse {
  qrCodeData: string;
  qrCodeImage: string;
}

export const pixService = {
  async createPix(data: PixCreateRequest): Promise<PixResponse> {
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

  async generateQRCode(pixId: string): Promise<QRCodeResponse> {
    try {
      const response = await api.get<QRCodeResponse>(`/api/pix/${pixId}/qrcode`);
      return response.data;
    } catch (error) {
      if (error instanceof AxiosError) {
        return handleApiError(error);
      }
      throw error;
    }
  }
};
