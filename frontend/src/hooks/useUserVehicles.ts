import { useState, useEffect, useCallback } from "react";
import { getUserVehicles } from "../api/vehicleApi";
import type { VehicleCard } from "../types/vehicle";

export function useUserVehicles() {
  const [vehicles, setVehicles] = useState<VehicleCard[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchVehicles = useCallback(async () => {
    setLoading(true);
    try {
      const data = await getUserVehicles();
      setVehicles(data);
    } catch (error) {
      console.error("Erro ao carregar veículos:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchVehicles();
  }, [fetchVehicles]);

  return { vehicles, loading, refetch: fetchVehicles };
}