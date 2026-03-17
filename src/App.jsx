import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import AdminDashboard from "./pages/AdminDashboard";
import Dashboard from "./pages/Dashboard";
import Marketplace from "./pages/Marketplace.tsx";
import MyPets from "./pages/MyPets";
import Register from "./pages/Register";
import SetPassword from "./pages/SetPassword";
import ForgotPassword from "./pages/ForgotPassword";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/forgot-password" element={<ForgotPassword />} />
      <Route path="/register" element={<Register />} />
      <Route path="/set-password" element={<SetPassword />} />
      <Route path="/dashboard" element={<AdminDashboard />} />
      <Route path="/user-dashboard" element={<Dashboard />} />
      <Route path="/pets" element={<MyPets />} />
      <Route path="/marketplace" element={<Marketplace />} />
      <Route path="/admin/marketplace" element={<Marketplace />} />
    </Routes>
  );
}

export default App;
