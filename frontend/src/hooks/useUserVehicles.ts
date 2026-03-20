import { useEffect, useState } from "react";
import { getUserVehicles } from "../api/vehicleApi";
import type { VehicleCard } from "../types/vehicle";

export const useUserVehicles = () => {
  const [vehicles, setVehicles] = useState<VehicleCard[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getUserVehicles()
      .then(setVehicles)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  return { vehicles, loading };
};