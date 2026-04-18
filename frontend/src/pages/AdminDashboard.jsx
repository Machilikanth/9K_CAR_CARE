import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllCars } from '../services/api';

const AdminDashboard = () => {
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCars();
  }, []);

  const fetchCars = async () => {
    try {
      const data = await getAllCars();
      setCars(data);
    } catch (err) {
      setError('Failed to load cars');
    }
    setLoading(false);
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'RECEIVED': return 'bg-gray-500';
      case 'IN_PROGRESS': return 'bg-blue-500';
      case 'COMPLETED': return 'bg-green-500';
      case 'DELIVERED': return 'bg-green-700';
      default: return 'bg-gray-500';
    }
  };

  if (loading) return <div className="text-center py-8">Loading...</div>;
  if (error) return <div className="text-center py-8 text-red-500">{error}</div>;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Admin Dashboard</h1>
      
      <div className="mb-4">
        <Link to="/admin/add-car" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
          Add New Car
        </Link>
      </div>

      <div className="overflow-x-auto">
        <table className="min-w-full bg-white shadow-md rounded">
          <thead>
            <tr className="bg-gray-200">
              <th className="px-4 py-2">Car Number</th>
              <th className="px-4 py-2">Status</th>
              <th className="px-4 py-2">Delivery Time</th>
              <th className="px-4 py-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {cars.map((car) => (
              <tr key={car.id} className="border-t">
                <td className="px-4 py-2">{car.carNumber}</td>
                <td className="px-4 py-2">
                  <span className={`px-2 py-1 text-white text-sm rounded ${getStatusColor(car.status)}`}>
                    {car.status}
                  </span>
                </td>
                <td className="px-4 py-2">{car.expectedDeliveryTime}</td>
                <td className="px-4 py-2">
                  <Link to={`/admin/car/${car.id}`} className="text-blue-500 hover:underline">
                    Manage
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminDashboard;