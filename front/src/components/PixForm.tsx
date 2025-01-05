"use client"
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import { pixService } from '../api/pix.requests';
import QRCodeDisplay from './QRCodeDisplay';

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

type PixKeyType = 'email' | 'phone' | 'document' | '';

export default function PixForm() {
  const [formData, setFormData] = useState({
    name: '',
    pixKeyType: '' as PixKeyType,
    pixKey: '',
    value: '',
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

  const validatePixKey = (type: PixKeyType, value: string): boolean => {
    switch (type) {
      case 'email':
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
      case 'phone':
        return /^\d{11}$/.test(value);
      case 'document':
        return /^\d{11}$|^\d{14}$/.test(value);
      default:
        return false;
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!formData.pixKeyType || !formData.pixKey || !formData.value) {
      toast.error('Por favor, preencha todos os campos obrigatórios');
      return;
    }

    if (!validatePixKey(formData.pixKeyType, formData.pixKey)) {
      toast.error('Formato de chave PIX inválido');
      return;
    }

    try {
      setLoading(true);
      let formattedPixKey = formData.pixKey;

      if (formData.pixKeyType === 'phone') {
        formattedPixKey = `+55${formData.pixKey}`;
      }

      const pixResponse = await pixService.createPix({
        pixKey: formattedPixKey,
        value: parseFloat(formData.value),
        name: formData.name || '',
        state: formData.state,
        city: formData.city
      });

      setQrCodeData({
        text: pixResponse.qrCodeText,
        image: pixResponse.qrCodeImage
      });

      toast.success('Pix criado e QR Code gerado com sucesso!');
    } catch (error) {
      toast.error('Erro ao processar sua solicitação');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const getPixKeyPlaceholder = () => {
    switch (formData.pixKeyType) {
      case 'email':
        return 'exemplo@email.com';
      case 'phone':
        return '11999999999';
      case 'document':
        return 'CPF/CNPJ (apenas números)';
      default:
        return 'Selecione o tipo de chave primeiro';
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
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Nome do beneficiário
            </label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              placeholder="Nome do beneficiário"
              required
            />
          </div>

          <div className="grid grid-cols-1 gap-6">
            <div>
              <label className="block text-sm font-medium text-gray-700">
                Tipo de Chave Pix *
              </label>
              <div className="mt-2 space-x-4">
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    value="email"
                    checked={formData.pixKeyType === 'email'}
                    onChange={(e) => setFormData({ ...formData, pixKeyType: e.target.value as PixKeyType, pixKey: '' })}
                    className="form-radio h-4 w-4 text-indigo-600"
                  />
                  <span className="ml-2">E-mail</span>
                </label>
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    value="phone"
                    checked={formData.pixKeyType === 'phone'}
                    onChange={(e) => setFormData({ ...formData, pixKeyType: e.target.value as PixKeyType, pixKey: '' })}
                    className="form-radio h-4 w-4 text-indigo-600"
                  />
                  <span className="ml-2">Celular</span>
                </label>
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    value="document"
                    checked={formData.pixKeyType === 'document'}
                    onChange={(e) => setFormData({ ...formData, pixKeyType: e.target.value as PixKeyType, pixKey: '' })}
                    className="form-radio h-4 w-4 text-indigo-600"
                  />
                  <span className="ml-2">CPF/CNPJ</span>
                </label>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Chave Pix *
              </label>
              <input
                type={formData.pixKeyType === 'email' ? 'email' : 'text'}
                value={formData.pixKey}
                onChange={(e) => setFormData({ ...formData, pixKey: e.target.value })}
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                placeholder={getPixKeyPlaceholder()}
                disabled={!formData.pixKeyType}
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Estado *
              </label>
              <select
                value={formData.state}
                onChange={(e) => setFormData({ ...formData, state: e.target.value })}
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                required
              >
                <option value="">Selecione um estado</option>
                {states
                  .sort((a: State, b: State) => a.nome.localeCompare(b.nome))
                  .map((state: State) => (
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
                onChange={(e) => setFormData({ ...formData, city: e.target.value })}
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                required
              >
                <option value="">Selecione uma cidade</option>
                {cities
                  .filter((city: City) => city.municipio.microrregiao.mesorregiao.UF.sigla === formData.state)
                  .sort((a: State, b: State) => a.nome.localeCompare(b.nome))
                  .map((city: City) => (
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
                onChange={(e) => setFormData({ ...formData, value: e.target.value })}
                className="mt-1 block w-full text-black rounded-md p-3 border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                placeholder="0,00"
                required
              />
            </div>
          </div>

          <div className="flex justify-center">
            <button
              type="submit"
              disabled={loading}
              className="w-full sm:w-auto bg-purple-600 px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-indigo-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
            >
              {loading ? 'Processando...' : 'Gerar Pix'}
            </button>
          </div>
        </form>

        {qrCodeData.image && (
          <QRCodeDisplay qrCodeData={qrCodeData} />
        )}
      </div>
    </div>
  );
}  