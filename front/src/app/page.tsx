"use client"
import { useState } from 'react';
import { pixService } from '@/api/pix.requests';

export default function Home() {
  const [pixValue, setPixValue] = useState<number>(0);
  const [description, setDescription] = useState<string>('');
  const [qrCodeData, setQRCodeData] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  const handleCreatePix = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const pixResponse = await pixService.createPix({
        value: pixValue,
        description
      });

      const qrCodeResponse = await pixService.generateQRCode(pixResponse.id);
      setQRCodeData(qrCodeResponse.qrCodeImage);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Ocorreu um erro ao criar o Pix');
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Criar Pagamento Pix</h1>
      
      <form onSubmit={handleCreatePix} className="space-y-4">
        <div>
          <label htmlFor="value" className="block mb-2">Valor:</label>
          <input
            type="number"
            id="value"
            value={pixValue}
            onChange={(e) => setPixValue(Number(e.target.value))}
            className="border p-2 rounded w-full"
            required
          />
        </div>

        <div>
          <label htmlFor="description" className="block mb-2">Descrição:</label>
          <input
            type="text"
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="border p-2 rounded w-full"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:bg-gray-400"
        >
          {loading ? 'Gerando...' : 'Gerar Pix'}
        </button>
      </form>

      {error && (
        <div className="mt-4 p-4 bg-red-100 text-red-700 rounded">
          {error}
        </div>
      )}

      {qrCodeData && (
        <div className="mt-4">
          <h2 className="text-xl font-bold mb-2">QR Code gerado:</h2>
          <img src={qrCodeData} alt="QR Code Pix" className="mx-auto" />
        </div>
      )}
    </main>
  );
} 