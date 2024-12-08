"use client"
import { useState } from 'react';
import { toast } from 'react-hot-toast';

export default function PixForm() {
  const [formData, setFormData] = useState({
    pixKey: '',
    value: '',
    description: '',
    userId: '1'
  });
  const [qrCodeData, setQrCodeData] = useState({
    text: '',
    image: ''
  });
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.pixKey || !formData.value || !formData.userId) {
      toast.error('Por favor, preencha todos os campos obrigatórios');
      return;
    }

    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/pix', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          pixKey: formData.pixKey,
          value: parseFloat(formData.value),
          description: formData.description || undefined,
          userId: parseInt(formData.userId)
        }),
      });

      if (!response.ok) {
        throw new Error('Erro ao criar Pix');
      }

      const data = await response.json();
      
      const qrResponse = await fetch(`http://localhost:8080/api/pix/${data.id}/qrcode`, {
        method: 'POST'
      });

      if (!qrResponse.ok) {
        throw new Error('Erro ao gerar QR Code');
      }

      const pixResponse = await fetch(`http://localhost:8080/api/pix/${data.id}`);
      const pixData = await pixResponse.json();

      setQrCodeData({
        text: pixData.qrCodeText,
        image: pixData.qrCodeImage
      });
      
      toast.success('Pix criado e QR Code gerado com sucesso!');
    } catch (error) {
      toast.error('Erro ao processar sua solicitação');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-xl overflow-hidden">
      <div className="px-6 py-8 sm:p-10">
        <h2 className="text-2xl font-bold text-gray-900 text-center mb-8">
          Gerar Cobrança Pix
        </h2>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label className="block text-sm font-medium text-gray-700">
                Chave Pix *
              </label>
              <input
                type="text"
                value={formData.pixKey}
                onChange={(e) => setFormData({...formData, pixKey: e.target.value})}
                className="mt-1 block w-full rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                placeholder="Digite a chave Pix"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Valor (R$) *
              </label>
              <input
                type="number"
                step="0.01"
                value={formData.value}
                onChange={(e) => setFormData({...formData, value: e.target.value})}
                className="mt-1 block w-full rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                placeholder="0,00"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Descrição
              </label>
              <textarea
                value={formData.description}
                onChange={(e) => setFormData({...formData, description: e.target.value})}
                className="mt-1 block w-full rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                placeholder="Descrição da cobrança (opcional)"
                rows={3}
              />
            </div>
          </div>

          <div className="flex justify-center">
            <button
              type="submit"
              disabled={loading}
              className="w-full sm:w-auto px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
            >
              {loading ? 'Processando...' : 'Gerar Pix'}
            </button>
          </div>
        </form>

        {qrCodeData.image && (
          <div className="mt-8 text-center">
            <div className="mb-4 p-4 bg-gray-50 rounded-lg inline-block">
              <img
                src={qrCodeData.image}
                alt="QR Code Pix"
                className="mx-auto w-48 h-48"
              />
            </div>
            <div className="space-y-2">
              <button
                onClick={() => {
                  navigator.clipboard.writeText(qrCodeData.text);
                  toast.success('Código Pix copiado!');
                }}
                className="text-sm text-indigo-600 hover:text-indigo-500 font-medium"
              >
                Copiar código Pix
              </button>
              <p className="text-xs text-gray-500">
                Use qualquer app de Pix para ler o QR Code ou cole o código copiado
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}  