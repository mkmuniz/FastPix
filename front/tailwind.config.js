/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          primary: '#4F46E5', // Indigo-600
          secondary: '#818CF8', // Indigo-400
          dark: '#3730A3', // Indigo-800
          light: '#E0E7FF', // Indigo-100
        },
      },
    },
  },
  plugins: [],
} 