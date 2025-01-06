"use client"
import { useState } from 'react';
import { Toaster } from 'react-hot-toast';
import Navbar from '@/components/Navbar';
import Header from '@/components/Header';
import PixForm from '@/components/PixForm';
import QRCodeDisplay from '@/components/QRCodeDisplay';

export default function Home() {
  const [qrCodeData, setQrCodeData] = useState<{text: string; image: string} | null>(null);

  return (
    <div className="min-h-screen bg-gray-900">
      <Toaster 
        position="top-right"
        toastOptions={{
          style: {
            background: '#1f2937',
            color: '#fff',
          },
        }}
      />
      <Navbar />
      <Header />
      
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <PixForm onQrCodeGenerated={setQrCodeData} />
          <QRCodeDisplay qrCodeData={qrCodeData} />
        </div>
      </section>
    </div>
  );
} 