import { BsCashStack } from 'react-icons/bs';

export default function Header() {
  return (
    <section className="h-screen bg-purple-600 text-white overflow-hidden flex items-center justify-center">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center">
          <div 
            className="animate-bounce mb-6"
            style={{ animationDuration: '2s' }}
          >
            <BsCashStack className="mx-auto text-6xl text-indigo-200" />
          </div>
          <h1 
            className="text-4xl font-extrabold sm:text-5xl md:text-6xl animate-fade-in-down"
          >
            FastPix
          </h1>
          <p 
            className="mt-3 max-w-md mx-auto text-xl text-indigo-100 sm:text-2xl md:mt-5 md:max-w-3xl animate-fade-in-up"
            style={{ animationDelay: '0.3s' }}
          >
            Gerando QRCode Pix de forma simples e rápida
          </p>
        </div>
      </div>
    </section>
  );
} 