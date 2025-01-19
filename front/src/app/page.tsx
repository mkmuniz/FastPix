"use client"

import { Toaster } from 'react-hot-toast';

import Navbar from '@/components/Navbar';
import Header from '@/components/Header';
import Pix from '@/components/Pix';

export default function Home() {
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
      <Pix />
    </div>
  );
} 