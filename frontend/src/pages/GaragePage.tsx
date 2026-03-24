import { useUserVehicles } from "../hooks/useUserVehicles";
import VehicleCard from "../components/VehicleCard/VehicleCard";
import "./GaragePage.css";

const GaragePage = () => {
  const { vehicles, loading } = useUserVehicles();

  if (loading) return <p>Carregando...</p>;

  return (
    <div className="garage-page">
      
      <div className="garage-header">
     <div className="garage-header-text">
       <h1>Minha garagem</h1>
       <p>Configure suas builds</p>
       </div>

       <button className="add-vehicle-btn">
       + Adicionar veículo
         </button>
       </div>

      {/* Lista de veículos */}
      <div className="garage-list">
        {vehicles.map((vehicle) => (
          <VehicleCard key={vehicle.id} vehicle={vehicle} />
        ))}
      </div>

      

    </div>
  );
};

export default GaragePage;



