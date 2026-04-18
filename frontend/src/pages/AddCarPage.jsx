import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createCar } from '../services/api';

const AddCarPage = () => {
  const [carNumber, setCarNumber] = useState('');
  const [expectedDeliveryTime, setExpectedDeliveryTime] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await createCar({ carNumber, expectedDeliveryTime });
      navigate('/admin');
    } catch (err) {
      setError('Failed to add car');
    }
    setLoading(false);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Add New Car</h1>
      
      <form onSubmit={handleSubmit} className="max-w-md mx-auto bg-white p-6 rounded-lg shadow-md">
        <div className="mb-4">
          <label className="block text-gray-700 mb-2">Car Number</label>
          <input
            type="text"
            value={carNumber}
            onChange={(e) => setCarNumber(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>
        
        <div className="mb-4">
          <label className="block text-gray-700 mb-2">Expected Delivery Time</label>
          <input
            type="datetime-local"
            value={expectedDeliveryTime}
            onChange={(e) => setExpectedDeliveryTime(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>
        
        {error && <p className="text-red-500 mb-4">{error}</p>}
        
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 disabled:bg-gray-400"
        >
          {loading ? 'Adding...' : 'Add Car'}
        </button>
      </form>
    </div>
  );
};

export default AddCarPage;