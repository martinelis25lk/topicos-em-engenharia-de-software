import { BrowserRouter, Routes, Route } from "react-router-dom";
import GaragePage from "../pages/GaragePage";
import VehiclePage from "../pages/VehiclePage";
import LoginPage from "../pages/LoginPage";
import ProtectedRoute from "../components/ProtectedRoute";
import AppLayout from "../pages/layouts/AppLayout";
import RegisterPage from "../pages/RegisterPage";
import FeedPage from "../pages/FeedPage";



export function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route element={<ProtectedRoute />}>
        <Route element={<AppLayout />}>
        <Route path="/" element={<FeedPage />} />
        <Route path="/feed" element={<FeedPage />} />  
        <Route path="/garage" element={<GaragePage />} />
        <Route path="/vehicle/:id" element={<VehiclePage />} />
      </Route>
    </Route>
   </Routes>
    </BrowserRouter>
  );
}