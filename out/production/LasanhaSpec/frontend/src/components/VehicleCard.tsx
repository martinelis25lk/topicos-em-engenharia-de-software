import { useNavigate } from "react-router-dom"

export function VehicleCard({ vehicle }) {

 const navigate = useNavigate()

 return (

  <div onClick={() => navigate(`/vehicle/${vehicle.id}`)}>

   <h2>{vehicle.model}</h2>
   <p>{vehicle.ownerName}</p>

  </div>

 )

}