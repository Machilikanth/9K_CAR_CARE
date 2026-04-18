# 9K Car Care Service

A React-based frontend application for managing car care services.

## Features

- **User Side**: Search car status by car number with real-time updates
- **Admin Panel**: Manage cars, services, and status updates

## Tech Stack

- React 18
- JavaScript
- Axios for API calls
- Tailwind CSS for styling
- React Router for navigation
- Vite for build tool

## Project Structure

```
src/
├── components/
├── pages/
│   ├── CarSearchPage.jsx
│   ├── AdminDashboard.jsx
│   ├── AddCarPage.jsx
│   └── CarDetailsPage.jsx
├── services/
│   └── api.js
├── utils/
├── App.jsx
└── index.js
```

## Installation

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the development server:
   ```bash
   npm run dev
   ```

3. Open [http://localhost:5173](http://localhost:5173) in your browser.

## API Endpoints

The app communicates with a backend at `http://localhost:8080/api`:

- `GET /cars/{carNumber}` - Get car details by number
- `GET /admin/cars` - Get all cars (admin)
- `POST /admin/cars` - Create new car
- `POST /admin/cars/{carId}/services` - Add services to car
- `PUT /admin/cars/{carId}/status` - Update car status

## Routes

- `/` - Car Search Page
- `/admin` - Admin Dashboard
- `/admin/add-car` - Add New Car
- `/admin/car/:id` - Manage Specific Car

## Status Colors

- RECEIVED: Gray
- IN_PROGRESS: Blue
- COMPLETED: Green
- DELIVERED: Dark Green