import { useState, useEffect } from 'react';
import { getCarByNumber } from '../services/api';
import Header from '../components/Header';
import SearchCard from '../components/SearchCard';
import CarDetailsCard from '../components/CarDetailsCard';
import ErrorAlert from '../components/ErrorAlert';

const CarSearchPage = () => {
  const [carNumber, setCarNumber] = useState('');
  const [carData, setCarData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSearch = async (number) => {
    setCarNumber(number);
    if (!number.trim()) return;
    setLoading(true);
    setError('');
    try {
      const data = await getCarByNumber(number);
      setCarData(data);
    } catch (err) {
      setError('Car not found or error occurred. Please try again.');
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

  return (
    <div className="min-h-screen">
      <Header />
      <SearchCard onSearch={handleSearch} loading={loading} />
      {error && <ErrorAlert message={error} />}
      {carData && <CarDetailsCard car={carData} />}
    </div>
  );
};

export default CarSearchPage;