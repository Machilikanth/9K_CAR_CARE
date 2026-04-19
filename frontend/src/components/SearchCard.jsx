import React, { useState } from 'react';
import { Search } from 'lucide-react';

const SearchCard = ({ onSearch, loading }) => {
  const [carNumber, setCarNumber] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(carNumber);
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-br from-blue-50 to-purple-50 pt-20">
      <div className="bg-white rounded-2xl shadow-lg p-8 w-full max-w-md">
        <h2 className="text-2xl font-semibold text-center mb-6 text-gray-800">Search Your Car</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Enter Car Number"
            value={carNumber}
            onChange={(e) => setCarNumber(e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-300"
            required
          />
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-gradient-to-r from-yellow-400 to-yellow-500 text-white py-3 rounded-lg font-semibold hover:from-yellow-500 hover:to-yellow-600 transition duration-300 flex items-center justify-center disabled:opacity-50"
          >
            <Search className="mr-2" size={20} />
            {loading ? 'Searching...' : 'Search'}
          </button>
        </form>
        <div className="mt-6 text-center">
          <div className="text-6xl">🚗</div>
          <p className="text-gray-600 mt-2">Find your car's service status</p>
        </div>
      </div>
    </div>
  );
};

export default SearchCard;