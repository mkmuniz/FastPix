"use client"
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import { pixService } from '../api/pix.requests';

interface State {
  id: number;
  sigla: string;
  nome: string;
}

interface City {
  id: number;
  nome: string;
  municipio: {
    microrregiao: {
      mesorregiao: {
        UF: {
          sigla: string;
        }
      }
    }
  }
}

export default function PixForm() {
  const [formData, setFormData] = useState({
    pixKey: '',
    value: '',
    description: '',
    userId: '1',
    state: '',
    city: ''
  });
  const [qrCodeData, setQrCodeData] = useState({
    text: '',
    image: ''
  });
  const [loading, setLoading] = useState(false);
  const [states, setStates] = useState([]);
  const [cities, setCities] = useState([]);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.pixKey || !formData.value || !formData.userId) {
      toast.error('Por favor, preencha todos os campos obrigatórios');
      return;
    }

    try {
      setLoading(true);
      
      const pixResponse = await pixService.createPix({
        pixKey: formData.pixKey,
        value: parseFloat(formData.value),
        description: formData.description || '',
        userId: parseInt(formData.userId),
        state: formData.state,
        city: formData.city
      });

      const qrCodeResponse = await pixService.generateQRCode(pixResponse.id);

      setQrCodeData({
        text: qrCodeResponse.qrCodeText,
        image: qrCodeResponse.qrCodeImage
      });
      
      toast.success('Pix criado e QR Code gerado com sucesso!');
    } catch (error) {
      toast.error('Erro ao processar sua solicitação');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const getStates = async () => {
    const response = await fetch('https://servicodados.ibge.gov.br/api/v1/localidades/estados');
    const data = await response.json();

    setStates(data);
  }
  
  const getCities = async () => {
    const response = await fetch('https://servicodados.ibge.gov.br/api/v1/localidades/distritos');
    const data = await response.json();

    setCities(data);
  }

  useEffect(() => {
    getStates();
    getCities();
  }, []);

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
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                placeholder="Digite a chave Pix"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Estado *
              </label>
              <select
                value={formData.state}
                onChange={(e) => setFormData({...formData, state: e.target.value})}
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                required
              >
                <option value="">Selecione um estado</option>
                {states.map((state: State) => (
                  <option key={state.id} value={state.sigla}>{state.nome}</option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Cidade *
              </label>
              <select
                value={formData.city}
                onChange={(e) => setFormData({...formData, city: e.target.value})}
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                required
              >
                <option value="">Selecione uma cidade</option>
                {cities.filter((city: City) => city.municipio.microrregiao.mesorregiao.UF.sigla === formData.state).map((city: City) => (
                  <option key={city.id} value={city.nome}>{city.nome}</option>
                ))}
              </select>
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
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
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
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
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
                src={qrCodeData.image.startsWith('data:image') 
                  ? qrCodeData.image 
                  : `data:image/png;base64,${qrCodeData.image}`}
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