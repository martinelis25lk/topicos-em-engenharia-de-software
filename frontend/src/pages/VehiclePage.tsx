import { useParams } from "react-router-dom";
import { useVehicleById } from "../hooks/useVehicleById";
import { PerformanceBar } from "../components/PerformanceBar";

const VehiclePage = () => {
  const { id } = useParams<{ id: string }>();
  const vehicleId = Number(id);

  const { data, loading, error } = useVehicleById(vehicleId);

  if (loading) return <p>Loading...</p>;
  if (error || !data) return <p>Error</p>;

  return (
    <div>
      {/* Nome do veículo */}
      <h1>{data.name}</h1>

      {/* Engine */}
      <p>{data.engine}</p>

      {/* Horsepower */}
      <PerformanceBar
        label="Horsepower"
        factoryValue={data.factoryHorsePower}
        currentValue={data.currentHorsePower}
        diff={data.horsepowerDiff}
        trend={data.horsepowerTrend}
      />

      {/* Torque */}
      <PerformanceBar
        label="Torque"
        factoryValue={data.factoryTorque}
        currentValue={data.currentTorque}
        diff={data.torqueDiff}
        trend={data.torqueTrend}
      />

      {/* Weight */}
      <PerformanceBar
        label="Weight"
        factoryValue={data.factoryWeight}
        currentValue={data.currentWeight}
        diff={data.weightDiff}
        trend={data.weightTrend}
      />
    </div>
  );
};

export default VehiclePage;