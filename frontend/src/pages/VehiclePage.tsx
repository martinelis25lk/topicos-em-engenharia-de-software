import { useParams } from "react-router-dom";
import { useVehicleById } from "../hooks/useVehicleById";
import { PerformanceBar } from "../components/PerformanceBar/PerformanceBar";
import "./VehiclePage.css";

const VehiclePage = () => {
  const { id } = useParams<{ id: string }>();
  const vehicleId = Number(id);

  const { data, loading, error } = useVehicleById(vehicleId);

  if (loading) return <p>Loading...</p>;
  if (error || !data) return <p>Error</p>;

  return (
    <div className="vehicle-page">

      {/* HERO */}
      <div className="vehicle-hero">
        <img src={data.imageUrl} alt={data.name} />
        <h1>{data.name}</h1>
        <p>{data.engine}</p>
      </div>

      {/* BOTÃO */}
      <div className="vehicle-actions">
        <button className="post-btn">Postar veículo</button>
      </div>

      {/* PERFORMANCE CARD */}
      <div className="vehicle-card">
        <h2>Performance Stats</h2>

        <PerformanceBar
          label="Horsepower"
          factoryValue={data.factoryHorsePower}
          currentValue={data.currentHorsePower}
          diff={data.horsepowerDiff}
          trend={data.horsepowerTrend}
        />

        <PerformanceBar
          label="Torque"
          factoryValue={data.factoryTorque}
          currentValue={data.currentTorque}
          diff={data.torqueDiff}
          trend={data.torqueTrend}
        />

        <PerformanceBar
          label="Weight"
          factoryValue={data.factoryWeight}
          currentValue={data.currentWeight}
          diff={data.weightDiff}
          trend={data.weightTrend}
        />
      </div>

      {/* INFO CARD */}
      <div className="vehicle-card small">
        <h2>Info</h2>
        <p><strong>Dono:</strong> {data.ownerName}</p>
        <p><strong>Ano:</strong> {data.year}</p>
        <p><strong>Motor:</strong> {data.engine}</p>
        <p><strong>Mods:</strong> {data.modsCount}</p>
      </div>

      {/* MODS CARD */}
      <div className="vehicle-card">
        <h2>Modificações</h2>

        <div className="mods-grid">
          {data.mods?.map((mod) => (
            <div key={mod.name} className="mod-card">
              {mod.name}
            </div>
          ))}
        </div>
      </div>

    </div>
  );
};

export default VehiclePage;