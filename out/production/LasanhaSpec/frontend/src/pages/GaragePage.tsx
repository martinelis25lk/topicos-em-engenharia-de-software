import { useUserVehicles } from "../hooks/useUserVehicles"//logica de dados
import { VehicleCard } from "../components/VehicleCard"// components reutilizaveis kkkk

export function GaragePage() {

 const { data, isLoading, error } = useUserVehicles()

 if (isLoading) {
  return <p>Loading vehicles...</p>
 }

 if (error) {
  return <p>Error loading vehicles</p>
 }

 return (

  <div>

   {data.map((vehicle) => (
     <VehicleCard
       key={vehicle.id}
       vehicle={vehicle}
     />
   ))}

  </div>

 )

}