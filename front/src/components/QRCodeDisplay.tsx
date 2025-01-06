import { FiDownload, FiCopy, FiGithub, FiGlobe } from 'react-icons/fi';
import { toast } from 'react-hot-toast';

interface QRCodeDisplayProps {
  qrCodeData: {
    text: string;
    image: string;
  } | null;
}

export default function QRCodeDisplay({ qrCodeData }: QRCodeDisplayProps) {
  const handleCopyCode = () => {
    if (qrCodeData?.text) {
      navigator.clipboard.writeText(qrCodeData.text);
      toast.success('Código PIX copiado!');
    }
  };

  const handleDownloadQRCode = () => {
    if (qrCodeData?.image) {
      const link = document.createElement('a');
      link.href = qrCodeData.image;
      link.download = 'qrcode-pix.png';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      toast.success('QR Code baixado!');
    }
  };

  return (
    <div className="space-y-4">
      <div className="bg-gray-800 rounded-lg shadow-xl overflow-hidden border border-gray-700 p-6">
        <h3 className="text-xl font-bold text-transparent bg-gradient-to-r from-blue-500 to-green-500 bg-clip-text text-center mb-6">
          QR Code PIX
        </h3>

        <div className="flex flex-col items-center space-y-6">
          <div className="w-64 h-64 bg-gray-700 rounded-lg flex items-center justify-center overflow-hidden">
            {qrCodeData ? (
              <img
                src={qrCodeData.image}
                alt="QR Code PIX"
                className="w-full h-full object-contain"
              />
            ) : (
              <div className="text-gray-500 text-center p-4">
                <p>QR Code será exibido aqui após gerar o PIX</p>
              </div>
            )}
          </div>

          <div className="flex flex-col w-full space-y-3">
            <button
              onClick={handleCopyCode}
              disabled={!qrCodeData}
              className="flex items-center justify-center space-x-2 w-full px-4 py-2 text-base font-medium rounded-md text-green-500 border-2 border-green-500 hover:bg-green-500 hover:text-white transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 disabled:opacity-50 disabled:hover:bg-transparent disabled:hover:text-green-500"
            >
              <FiCopy className="w-5 h-5" />
              <span>Copiar Código</span>
            </button>

            <button
              onClick={handleDownloadQRCode}
              disabled={!qrCodeData}
              className="flex items-center justify-center space-x-2 w-full px-4 py-2 text-base font-medium rounded-md text-green-500 border-2 border-green-500 hover:bg-green-500 hover:text-white transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 disabled:opacity-50 disabled:hover:bg-transparent disabled:hover:text-green-500"
            >
              <FiDownload className="w-5 h-5" />
              <span>Baixar QR Code</span>
            </button>
          </div>
        </div>
      </div>

      <div className="bg-gray-800 rounded-lg shadow-xl overflow-hidden border border-gray-700 p-6">
        <h4 className="text-lg font-semibold text-transparent bg-gradient-to-r from-blue-500 to-green-500 bg-clip-text text-center mb-4">
          Desenvolvido por Mikael Muniz
        </h4>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="space-y-4">
            <div className="space-y-2">
              <h5 className="text-sm font-medium text-gray-400">Links</h5>
              <div className="flex flex-col space-y-3">
                <a
                  href="https://mkmuniz.dev/"
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center space-x-2 text-gray-400 hover:text-green-500 transition-colors duration-200"
                >
                  <FiGlobe className="w-5 h-5" />
                  <span>Portfolio: mkmuniz.dev</span>
                </a>
                <a
                  href="https://github.com/mkmuniz"
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center space-x-2 text-gray-400 hover:text-green-500 transition-colors duration-200"
                >
                  <FiGithub className="w-5 h-5" />
                  <span>GitHub: @mkmuniz</span>
                </a>
              </div>
            </div>
          </div>

          <div className="space-y-4">
            <div className="space-y-2">
              <h5 className="text-sm font-medium text-gray-400">Sobre o Projeto</h5>
              <div className="text-sm text-gray-500 space-y-2">
                <p>
                  FastPix é um gerador de QR Code PIX open source, desenvolvido com Spring Boot e Next.js.
                </p>
                <p>
                  Contribuições são bem-vindas! Visite o repositório no GitHub.
                </p>
              </div>
            </div>

            <div className="space-y-2">
              <h5 className="text-sm font-medium text-gray-400">Tecnologias</h5>
              <div className="flex flex-wrap gap-2">
                <span className="px-2 py-1 text-xs rounded-full bg-gray-700 text-gray-300">Spring Boot</span>
                <span className="px-2 py-1 text-xs rounded-full bg-gray-700 text-gray-300">Next.js</span>
                <span className="px-2 py-1 text-xs rounded-full bg-gray-700 text-gray-300">TypeScript</span>
                <span className="px-2 py-1 text-xs rounded-full bg-gray-700 text-gray-300">Tailwind CSS</span>
              </div>
            </div>
          </div>
        </div>

        <div className="mt-4 pt-4 border-t border-gray-700 text-center">
          <p className="text-xs text-gray-500">
            © 2024 FastPix - Todos os direitos reservados
          </p>
        </div>
      </div>
    </div>
  );
} 