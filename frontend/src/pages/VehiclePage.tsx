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
      <h1>{data.model}</h1>
      <p>Owner: {data.ownerName}</p>

      <PerformanceBar
     label="Horsepower"
     factoryValue={data.factoryHorsePower}
     currentValue={data.currentHorsePower}
/>

<PerformanceBar
     label="Torque"
     factoryValue={data.factoryTorque}
     currentValue={data.currentTorque}
/>

<PerformanceBar
     label="Weight"
     factoryValue={data.factoryWeight}
     currentValue={data.currentWeight}
/>
    </div>
  );
};

export default VehiclePage;