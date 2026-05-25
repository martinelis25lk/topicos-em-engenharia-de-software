import { BrowserRouter, Routes, Route } from "react-router-dom";
import GaragePage from "../pages/GaragePage";
import VehiclePage from "../pages/VehiclePage";
import LoginPage from "../pages/LoginPage";
import ProtectedRoute from "../components/ProtectedRoute";

export function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/login" element={<LoginPage />} />

        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<GaragePage />} />
          <Route path="/vehicle/:id" element={<VehiclePage />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}