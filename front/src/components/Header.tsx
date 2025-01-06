import Image from 'next/image';

export default function Header() {
  return (
    <section className="bg-gray-900 text-white py-20">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center">
          <div className="mb-6">
            <Image
              src="/pix-logo.png" 
              alt="Logo Pix" 
              width={100}
              height={100}
              className="h-full mx-auto"
            />
          </div>
          
          <h1 className="text-4xl font-extrabold sm:text-5xl md:text-6xl bg-clip-text text-transparent bg-gradient-to-r from-blue-500 to-green-500">
            Gere e personalize QRCode Pix
          </h1>
          
          <p className="mt-6 max-w-2xl mx-auto text-lg text-gray-300">
            Crie QR Codes PIX de forma rápida e segura para suas cobranças. 
            Personalize com seus dados e gere códigos prontos para impressão.
          </p>
        </div>
      </div>
    </section>
  );
} 