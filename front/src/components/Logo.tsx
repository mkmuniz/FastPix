export default function Logo() {
  return (
    <div className="mb-8 animate-fade-in-down">
      <div className="relative w-24 h-24 mx-auto">
        <div className="absolute inset-0 bg-white rounded-full opacity-20 animate-pulse"></div>
        <div className="absolute inset-2 bg-white rounded-full opacity-30"></div>
        <div className="absolute inset-0 flex items-center justify-center">
          <svg
            className="w-12 h-12 text-white"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          >
            <path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" />
          </svg>
        </div>
      </div>
    </div>
  );
} 