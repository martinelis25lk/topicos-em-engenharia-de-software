import { useEffect, useState } from "react"
import { VehicleDetail } from "../types/vehicle"
import { getVehicleById } from "../api/vehicleApi"

export function useVehicleById(id: number) {

 const [data, setData] = useState<VehicleDetail | null>(null)
 const [loading, setLoading] = useState(true)
 const [error, setError] = useState(false)

 useEffect(() => {

  async function fetchVehicle() {
   try {

    const response = await getVehicleById(id)
    setData(response)

   } catch (err) {
    setError(true)
   } finally {
    setLoading(false)
   }
  }

  fetchVehicle()

 }, [id])

 return { data, loading, error }
}