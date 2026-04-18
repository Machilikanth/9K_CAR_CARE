import React from 'react';
import { AlertTriangle } from 'lucide-react';

const ErrorAlert = ({ message }) => {
  return (
    <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg flex items-center justify-center max-w-md mx-auto mt-4">
      <AlertTriangle className="mr-2" size={20} />
      <span>{message}</span>
    </div>
  );
};

export default ErrorAlert;