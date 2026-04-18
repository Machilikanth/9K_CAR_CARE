import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// API functions
export const getCarByNumber = async (carNumber) => {
  try {
    const response = await api.get(`/cars/${carNumber}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getAllCars = async () => {
  try {
    const response = await api.get('/admin/cars');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createCar = async (carData) => {
  try {
    const response = await api.post('/admin/cars', carData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const addServicesToCar = async (carId, services) => {
  try {
    const response = await api.post(`/admin/cars/${carId}/services`, { services });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const updateCarStatus = async (carId, status) => {
  try {
    const response = await api.put(`/admin/cars/${carId}/status`, { status });
    return response.data;
  } catch (error) {
    throw error;
  }
};