"use client"

import React from 'react';
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';

import { pixService } from '../api/pix.requests';
import { StateType, CityType, PixKeyType, PixFormProps } from '../types/form.types';
import { getLocations } from '../api/forms.requests';

export default function PixForm({ onQrCodeGenerated }: PixFormProps) {
  const [formData, setFormData] = useState({
    name: '',
    pixKeyType: '' as PixKeyType,
    pixKey: '',
    value: '',
    state: '',
    city: ''
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

      if (formData.pixKeyType === 'phone') formattedPixKey = `+55${formData.pixKey}`;

      const pixResponse = await pixService.createPix({
        pixKey: formattedPixKey,
        value: parseFloat(formData.value),
        name: formData.name || '',
        state: formData.state,
        city: formData.city
      });

      onQrCodeGenerated({
        text: pixResponse.qrCodeText,
        image: pixResponse.qrCodeImage
      });

      toast.success('Pix criado e QR Code gerado com sucesso!');
    } catch (error) {
      toast.error('Erro ao processar sua solicitação');
      console.error(error);
      onQrCodeGenerated(null);
    } finally {
      setLoading(false);
    }
  };

  const getPixKeyPlaceholder = () => {
    switch (formData.pixKeyType) {
      case 'email':
        return 'exemplo@email.com';
      case 'phone':
        return '(11) 99999-9999';
      case 'document':
        return 'CPF/CNPJ (apenas números)';
      default:
        return 'Selecione o tipo de chave primeiro';
    }
  };

  const fetchData = async () => {
    const [statesData, citiesData] = await Promise.all([
      getLocations('estados'),
      getLocations('distritos')
    ]);
    setStates(statesData);
    setCities(citiesData);
  };
  
  useEffect(() => {
    fetchData();
  }, []);

  const generateStates = () => {
    return states
      .sort((a: StateType, b: StateType) => a.nome.localeCompare(b.nome))
      .map((state: StateType) => (
        <option key={state.id} value={state.sigla}>{state.nome}</option>
      ));
  };

  const generateCities = () => {
    return cities
      .filter((city: CityType) => city.municipio.microrregiao.mesorregiao.UF.sigla === formData.state)
      .sort((a: StateType, b: StateType) => a.nome.localeCompare(b.nome))
      .map((city: CityType) => (
        <option key={city.id} value={city.nome}>{city.nome}</option>
      ))
  };

  return (
    <div className="bg-gray-800 rounded-lg shadow-xl overflow-hidden border border-gray-700">
      <div className="px-6 py-8 sm:p-10">
        <h2 className="text-2xl font-bold text-transparent bg-gradient-to-r from-blue-500 to-green-500 bg-clip-text text-center mb-8">
          Gerar Cobrança Pix
        </h2>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-sm font-medium text-gray-300">
              Nome do beneficiário *
            </label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="mt-1 block w-full bg-gray-700 text-white rounded-md p-3 border-gray-600 focus:border-green-500 focus:ring-green-500 placeholder-gray-400"
              placeholder="Nome do beneficiário"
              required
            />
          </div>

          <div className="grid grid-cols-1 gap-6">
            <div>
              <label className="block text-sm font-medium text-gray-300">
                Tipo de Chave Pix *
              </label>
              <div className="mt-2 space-x-4">
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    value="email"
                    checked={formData.pixKeyType === 'email'}
                    onChange={(e) => setFormData({ ...formData, pixKeyType: e.target.value as PixKeyType, pixKey: '' })}
                    className="form-radio h-4 w-4 text-green-500 border-gray-600 focus:ring-green-500"
                  />
                  <span className="ml-2 text-gray-300">E-mail</span>
                </label>
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    value="phone"
                    checked={formData.pixKeyType === 'phone'}
                    onChange={(e) => setFormData({ ...formData, pixKeyType: e.target.value as PixKeyType, pixKey: '' })}
                    className="form-radio h-4 w-4 text-green-500 border-gray-600 focus:ring-green-500"
                  />
                  <span className="ml-2 text-gray-300">Celular</span>
                </label>
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    value="document"
                    checked={formData.pixKeyType === 'document'}
                    onChange={(e) => setFormData({ ...formData, pixKeyType: e.target.value as PixKeyType, pixKey: '' })}
                    className="form-radio h-4 w-4 text-green-500 border-gray-600 focus:ring-green-500"
                  />
                  <span className="ml-2 text-gray-300">CPF/CNPJ</span>
                </label>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-300">
                Chave Pix *
              </label>
              <input
                type={formData.pixKeyType === 'email' ? 'email' : 'text'}
                value={formData.pixKey}
                onChange={(e) => setFormData({ ...formData, pixKey: e.target.value })}
                className="mt-1 block w-full bg-gray-700 text-white rounded-md p-3 border-gray-600 focus:border-green-500 focus:ring-green-500 placeholder-gray-400"
                placeholder={getPixKeyPlaceholder()}
                disabled={!formData.pixKeyType}
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-300">
                Estado
              </label>
              <select
                value={formData.state}
                onChange={(e) => setFormData({ ...formData, state: e.target.value })}
                className="mt-1 block w-full bg-gray-700 text-white rounded-md p-3 border-gray-600 focus:border-green-500 focus:ring-green-500"
              >
                <option value="">Selecione um estado</option>
                {generateStates()}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-300">
                Cidade
              </label>
              <select
                value={formData.city}
                onChange={(e) => setFormData({ ...formData, city: e.target.value })}
                className="mt-1 block w-full bg-gray-700 text-white rounded-md p-3 border-gray-600 focus:border-green-500 focus:ring-green-500"
              >
                <option value="">Selecione uma cidade</option>
                {generateCities()}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-300">
                Valor (R$) *
              </label>
              <input
                type="number"
                step="0.01"
                value={formData.value}
                onChange={(e) => setFormData({ ...formData, value: e.target.value })}
                className="mt-1 block w-full bg-gray-700 text-white rounded-md p-3 border-gray-600 focus:border-green-500 focus:ring-green-500 placeholder-gray-400"
                placeholder="0,00"
                required
              />
            </div>
          </div>

          <div className="flex justify-center">
            <button
              type="submit"
              disabled={loading}
              className="w-full sm:w-auto px-6 py-3 text-base font-medium rounded-md text-green-500 border-2 border-green-500 hover:bg-green-500 hover:text-white transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 disabled:opacity-50 disabled:hover:bg-transparent disabled:hover:text-green-500"
            >
              {loading ? 'Processando...' : 'Gerar Pix'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}  