import { Routes, Route } from "react-router-dom";
import GaragePage from "./pages/GaragePage";
import VehiclePage from "./pages/VehiclePage";
import "./assets/styles.css";


function App() {
  return (
    <Routes>
      <Route path="/garage" element={<GaragePage />} />
      <Route path="/vehicle/:id" element={<VehiclePage />} />
    </Routes>
  );
}

export default App;