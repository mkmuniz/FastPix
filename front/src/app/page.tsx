"use client"
import { Toaster } from 'react-hot-toast';
import Navbar from '@/components/Navbar';
import Header from '@/components/Header';
import PixForm from '@/components/PixForm';

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-50 to-gray-100">
      <Toaster position="top-right" />
      <Navbar />
      <Header />
      
      <section className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <PixForm />
      </section>
    </div>
  );
} 