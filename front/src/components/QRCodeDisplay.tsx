import React from 'react';
import { toast } from 'react-hot-toast';

interface QRCodeDisplayProps {
  qrCodeData: {
    text: string;
    image: string;
  };
}

const QRCodeDisplay: React.FC<QRCodeDisplayProps> = ({ qrCodeData }) => {
  const handleDownloadQRCode = () => {
    const link = document.createElement('a');
    link.href = qrCodeData.image.startsWith('data:image')
      ? qrCodeData.image
      : `data:image/png;base64,${qrCodeData.image}`;
    link.download = 'qrcode.png';
    link.click();
  };

  return (
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
      <div className="space-y-2 flex flex-col items-center">
        <button
          onClick={() => {
            navigator.clipboard.writeText(qrCodeData.text);
            toast.success('Código Pix copiado!');
          }}
          className="text-sm text-indigo-600 hover:text-indigo-500 font-medium"
        >
          Copiar código Pix
        </button>
        <button
          onClick={handleDownloadQRCode}
          className="text-sm text-indigo-600 hover:text-indigo-500 font-medium"
        >
          Baixar QR Code
        </button>
        <p className="text-xs text-gray-500">
          Use qualquer app de Pix para ler o QR Code ou cole o código copiado
        </p>
      </div>
    </div>
  );
};

export default QRCodeDisplay; 