import type { VehicleCard as VehicleCardType } from "../types/vehicle";
import { PerformanceBar } from "./PerformanceBar";

interface Props {
  vehicle: VehicleCardType;
}

const VehicleCard = ({ vehicle }: Props) => {
  return (
    <div className="card">
      {/* Imagem */}
      <div className="card-image">
        <img src={vehicle.imageUrl} alt={vehicle.name} />
      </div>

      {/* Conteúdo */}
      <div className="card-content">
        <h2>{vehicle.name}</h2>
        <p className="engine">{vehicle.engine}</p>

        {/* Horsepower */}
        <PerformanceBar
          label="Horsepower"
          factoryValue={vehicle.factoryHorsePower}
          currentValue={vehicle.currentHorsePower}
          diff={vehicle.horsepowerDiff}
          trend={vehicle.horsepowerTrend}
        />

        {/* Torque */}
        <PerformanceBar
          label="Torque"
          factoryValue={vehicle.factoryTorque}
          currentValue={vehicle.currentTorque}
          diff={vehicle.torqueDiff}
          trend={vehicle.torqueTrend}
        />

        {/* Weight */}
        <PerformanceBar
          label="Weight"
          factoryValue={vehicle.factoryWeight}
          currentValue={vehicle.currentWeight}
          diff={vehicle.weightDiff}
          trend={vehicle.weightTrend}
        />
      </div>
    </div>
  );
};

export default VehicleCard;