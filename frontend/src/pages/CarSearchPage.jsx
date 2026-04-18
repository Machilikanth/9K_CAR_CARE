import { useState, useEffect } from 'react';
import { getCarByNumber } from '../services/api';

const CarSearchPage = () => {
  const [carNumber, setCarNumber] = useState('');
  const [carData, setCarData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSearch = async () => {
    if (!carNumber.trim()) return;
    setLoading(true);
    setError('');
    try {
      const data = await getCarByNumber(carNumber);
      setCarData(data);
    } catch (err) {
      setError('Car not found or error occurred');
      setCarData(null);
    }
    setLoading(false);
  };

  useEffect(() => {
    if (carData) {
      const interval = setInterval(async () => {
        try {
          const data = await getCarByNumber(carNumber);
          setCarData(data);
        } catch (err) {
          // Ignore errors during polling
        }
      }, 10000); // 10 seconds

      return () => clearInterval(interval);
    }
  }, [carData, carNumber]);

  const getStatusColor = (status) => {
    switch (status) {
      case 'RECEIVED': return 'bg-gray-500';
      case 'IN_PROGRESS': return 'bg-blue-500';
      case 'COMPLETED': return 'bg-green-500';
      case 'DELIVERED': return 'bg-green-700';
      default: return 'bg-gray-500';
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-center mb-8">9K Car Care Service</h1>
      
      <div className="max-w-md mx-auto mb-8">
        <input
          type="text"
          placeholder="Enter Car Number"
          value={carNumber}
          onChange={(e) => setCarNumber(e.target.value)}
          className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button
          onClick={handleSearch}
          disabled={loading}
          className="w-full mt-4 bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 disabled:bg-gray-400"
        >
          {loading ? 'Searching...' : 'Search'}
        </button>
      </div>

      {error && <p className="text-red-500 text-center">{error}</p>}

      {carData && (
        <div className="max-w-2xl mx-auto bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold mb-4">Car Details</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <p><strong>Car Number:</strong> {carData.carNumber}</p>
              <p><strong>Status:</strong> 
                <span className={`inline-block px-2 py-1 text-white text-sm rounded ${getStatusColor(carData.status)}`}>
                  {carData.status}
                </span>
              </p>
              <p><strong>Expected Delivery Time:</strong> {carData.expectedDeliveryTime}</p>
            </div>
            <div>
              <h3 className="text-lg font-semibold mb-2">Services</h3>
              <ul className="list-disc list-inside">
                {carData.services.map((service, index) => (
                  <li key={index}>{service.name} - ${service.price}</li>
                ))}
              </ul>
              <p className="mt-2"><strong>Total Price:</strong> ${carData.totalPrice}</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default CarSearchPage;