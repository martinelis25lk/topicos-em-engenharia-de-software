import { useQuery } from "@tanstack/react-query"
import { getUserVehicles } from "../api/vehicleApi"

export function useUserVehicles() {

 return useQuery({
  queryKey: ["userVehicles"],
  queryFn: getUserVehicles
 })

}

//logica de dados