import React from 'react';
import { Car } from 'lucide-react';

const Header = () => {
  return (
    <header className="bg-gradient-to-r from-blue-600 to-purple-600 text-white py-4 px-6 shadow-lg fixed top-0 left-0 right-0 z-10">
      <div className="container mx-auto flex items-center justify-center">
        <Car className="mr-2" size={32} />
        <h1 className="text-2xl font-bold">9K Car Care Service</h1>
      </div>
    </header>
  );
};

export default Header;