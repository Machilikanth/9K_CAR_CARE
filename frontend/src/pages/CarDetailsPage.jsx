import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getCarByNumber, addServicesToCar, updateCarStatus } from '../services/api';

const CarDetailsPage = () => {
  const { id } = useParams();
  const [car, setCar] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedServices, setSelectedServices] = useState([]);
  const [newStatus, setNewStatus] = useState('');

  // Mock services list - in real app, fetch from API
  const availableServices = [
    { id: 1, name: 'Oil Change', price: 50 },
    { id: 2, name: 'Tire Rotation', price: 20 },
    { id: 3, name: 'Brake Inspection', price: 30 },
    { id: 4, name: 'Car Wash', price: 15 },
  ];

  useEffect(() => {
    fetchCar();
  }, [id]);

  const fetchCar = async () => {
    try {
      // Assuming id is carNumber for simplicity
      const data = await getCarByNumber(id);
      setCar(data);
      setNewStatus(data.status);
    } catch (err) {
      setError('Failed to load car details');
    }
    setLoading(false);
  };

  const handleAddServices = async () => {
    if (selectedServices.length === 0) return;
    try {
      await addServicesToCar(id, selectedServices);
      fetchCar(); // Refresh data
      setSelectedServices([]);
    } catch (err) {
      setError('Failed to add services');
    }
  };

  const handleUpdateStatus = async () => {
    try {
      await updateCarStatus(id, newStatus);
      fetchCar(); // Refresh data
    } catch (err) {
      setError('Failed to update status');
    }
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
  if (!car) return <div className="text-center py-8">Car not found</div>;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Manage Car: {car.carNumber}</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Car Details */}
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-xl font-bold mb-4">Car Details</h2>
          <p><strong>Status:</strong> 
            <span className={`ml-2 px-2 py-1 text-white text-sm rounded ${getStatusColor(car.status)}`}>
              {car.status}
            </span>
          </p>
          <p><strong>Delivery Time:</strong> {car.expectedDeliveryTime}</p>
          <h3 className="text-lg font-semibold mt-4 mb-2">Services</h3>
          <ul className="list-disc list-inside">
            {car.services.map((service, index) => (
              <li key={index}>{service.name} - ${service.price}</li>
            ))}
          </ul>
          <p className="mt-2"><strong>Total Price:</strong> ${car.totalPrice}</p>
        </div>

        {/* Update Status */}
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-xl font-bold mb-4">Update Status</h2>
          <select
            value={newStatus}
            onChange={(e) => setNewStatus(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4"
          >
            <option value="RECEIVED">RECEIVED</option>
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="COMPLETED">COMPLETED</option>
            <option value="DELIVERED">DELIVERED</option>
          </select>
          <button
            onClick={handleUpdateStatus}
            className="w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
          >
            Update Status
          </button>
        </div>

        {/* Add Services */}
        <div className="bg-white p-6 rounded-lg shadow-md md:col-span-2">
          <h2 className="text-xl font-bold mb-4">Add Services</h2>
          <div className="grid grid-cols-2 gap-4 mb-4">
            {availableServices.map((service) => (
              <label key={service.id} className="flex items-center">
                <input
                  type="checkbox"
                  checked={selectedServices.includes(service.id)}
                  onChange={(e) => {
                    if (e.target.checked) {
                      setSelectedServices([...selectedServices, service.id]);
                    } else {
                      setSelectedServices(selectedServices.filter(id => id !== service.id));
                    }
                  }}
                  className="mr-2"
                />
                {service.name} - ${service.price}
              </label>
            ))}
          </div>
          <button
            onClick={handleAddServices}
            disabled={selectedServices.length === 0}
            className="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600 disabled:bg-gray-400"
          >
            Add Selected Services
          </button>
        </div>
      </div>
    </div>
  );
};

export default CarDetailsPage;