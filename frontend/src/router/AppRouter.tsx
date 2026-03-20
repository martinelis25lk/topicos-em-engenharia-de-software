import { BrowserRouter, Routes, Route } from "react-router-dom"
import GaragePage from "../pages/GaragePage"; // default import
import  VehiclePage  from "../pages/VehiclePage"; // named imports



export function AppRouter() {

 return (

  <BrowserRouter>

   <Routes>

    <Route path="/" element={<GaragePage />} />
    <Route path="/vehicle/:id" element={<VehiclePage />} />

   </Routes>

  </BrowserRouter>

 )

}