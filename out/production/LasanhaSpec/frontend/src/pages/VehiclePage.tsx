import { useParams } from "react-router-dom"
import { useVehicleById } from "../hooks/useVehicleById"
import { PerformanceBar } from "../components/PerformanceBar"

export function VehiclePage() {

 const { id } = useParams()

 const { data, loading, error } = useVehicleById(Number(id))

 if (loading) return <p>Loading...</p>
 if (error || !data) return <p>Error</p>

 return (

  <div>

   <h1>{data.model}</h1>
   <p>Owner: {data.ownerName}</p>

   <PerformanceBar
     label="Horsepower"
     factoryValue={data.factoryHorsepower}
     currentValue={data.currentHorsepower}
   />

   <PerformanceBar
     label="Torque"
     factoryValue={data.factoryTorque}
     currentValue={data.currentTorque}
   />

   <PerformanceBar
     label="Weight"
     factoryValue={data.weight}
     currentValue={data.weight}
     reverseLogic
   />

  </div>

 )

}