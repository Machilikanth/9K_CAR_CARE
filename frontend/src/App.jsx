import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CarSearchPage from './pages/CarSearchPage';
import AdminDashboard from './pages/AdminDashboard';
import AddCarPage from './pages/AddCarPage';
import CarDetailsPage from './pages/CarDetailsPage';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Routes>
          <Route path="/" element={<CarSearchPage />} />
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/add-car" element={<AddCarPage />} />
          <Route path="/admin/car/:id" element={<CarDetailsPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;