import { useEffect, useState } from "react";
import VehicleCard from "../components/VehicleCard/VehicleCard";
import { getFeedVehicles } from "../api/vehicleApi";
import type { VehicleCard as VehicleCardType } from "../types/vehicle";
import "./GaragePage.css";

export default function FeedPage() {
  const [vehicles, setVehicles] = useState<VehicleCardType[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadFeed() {
      try {
        const data = await getFeedVehicles();
        setVehicles(data);
      } catch (error) {
        console.error("Erro ao carregar feed:", error);
      } finally {
        setLoading(false);
      }
    }

    loadFeed();
  }, []);

  if (loading) return <p>Carregando feed...</p>;

  return (
    <div className="garage-page">
      <div className="garage-header">
        <div className="garage-header-text">
          <h1>Feed</h1>
          <p>Garagens da comunidade</p>
        </div>
      </div>

      <div className="garage-list">
        {vehicles.map((vehicle) => (
          <VehicleCard key={vehicle.id} vehicle={vehicle} />
        ))}
      </div>
    </div>
  );
}