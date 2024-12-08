import { api, handleApiError } from './request.config';
import { AxiosError } from 'axios';

interface CreatePixRequest {
  value: number;
  description: string;
  pixKey: string;
  userId: number;
}

interface PixResponse {
  id: string;
  value: number;
  description?: string;
  status: string;
  createdAt: string;
}

interface QRCodeResponse {
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

  async generateQRCode(pixId: string): Promise<QRCodeResponse> {
    try {
      await api.post(`/api/pix/${pixId}/qrcode`);
      
      const pixResponse = await api.get<{
        qrCodeText: string;
        qrCodeImage: string;
      }>(`/api/pix/${pixId}`);

      const qrCodeImage = pixResponse.data.qrCodeImage.replace(/^data:image\/[a-z]+;base64,/, '');

      return {
        qrCodeText: pixResponse.data.qrCodeText,
        qrCodeImage: qrCodeImage
      };
    } catch (error) {
      if (error instanceof AxiosError) {
        return handleApiError(error);
      }
      throw error;
    }
  }
};
