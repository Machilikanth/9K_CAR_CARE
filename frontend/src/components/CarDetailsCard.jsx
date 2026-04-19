import React from 'react';
import { CheckCircle, Clock, Truck, Wrench } from 'lucide-react';

const CarDetailsCard = ({ car }) => {
  const getStatusColor = (status) => {
    switch (status) {
      case 'RECEIVED': return 'bg-gray-500';
      case 'IN_PROGRESS': return 'bg-blue-500';
      case 'COMPLETED': return 'bg-green-500';
      case 'DELIVERED': return 'bg-green-700';
      default: return 'bg-gray-500';
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case 'COMPLETED': return <CheckCircle className="inline mr-1" size={16} />;
      case 'DELIVERED': return <Truck className="inline mr-1" size={16} />;
      default: return <Clock className="inline mr-1" size={16} />;
    }
  };

  return (
    <div className="bg-white rounded-2xl shadow-lg p-8 w-full max-w-md mx-auto mt-8 animate-fade-in">
      <h3 className="text-xl font-bold text-center mb-4">{car.carNumber}</h3>
      <div className="mb-4 text-center">
        <span className={`inline-block px-3 py-1 rounded-full text-white text-sm font-semibold ${getStatusColor(car.status)}`}>
          {getStatusIcon(car.status)} {car.status.replace('_', ' ')}
        </span>
      </div>
      <p className="text-gray-600 mb-2"><strong>Expected Delivery:</strong> {car.expectedDeliveryTime}</p>
      <div className="mb-4">
        <strong className="flex items-center"><Wrench className="mr-1" size={16} /> Services:</strong>
        <ul className="list-disc list-inside ml-4">
          {car.services.map((service, index) => (
            <li key={index}>{service.name} - ${service.price}</li>
          ))}
        </ul>
      </div>
      <p className="text-2xl font-bold text-green-600 text-center bg-green-50 py-2 rounded-lg">Total: ${car.totalPrice}</p>
    </div>
  );
};

export default CarDetailsCard;