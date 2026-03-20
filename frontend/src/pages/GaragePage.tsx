import { useUserVehicles } from "../hooks/useUserVehicles";
import VehicleCard from "../components/VehicleCard";

const GaragePage = () => {
  const { vehicles, loading } = useUserVehicles();

  if (loading) return <p>Carregando...</p>;

  return (
    <div>
      {vehicles.map((vehicle) => (
        <VehicleCard key={vehicle.id} vehicle={vehicle} />
      ))}
    </div>
  );
};

export default GaragePage;