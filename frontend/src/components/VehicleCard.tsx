import type { VehicleCard as VehicleCardType } from "../types/vehicle";

interface VehicleCardProps {
  vehicle: VehicleCardType;
}

const VehicleCard = ({ vehicle }: VehicleCardProps) => {
  const isPositive = vehicle.modificationsCount >= 0; // ou qualquer lógica que você queira

  return (
    <div>
      <h2>{vehicle.model}</h2>
      <img src={vehicle.imageUrl} alt={vehicle.model} width={200} />
      <p>Owner: {vehicle.ownerName}</p>
      <p>{vehicle.modificationsCount} modifications</p>
    </div>
  );
};

export default VehicleCard;