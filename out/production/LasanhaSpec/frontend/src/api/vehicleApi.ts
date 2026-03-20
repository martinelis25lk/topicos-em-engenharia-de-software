export async function  getUserVehicles() {
    const response = await fetch("http://localhost:8080/user-vehicles")

    if(!response.ok){
        throw new  Error("Failed to fetch vehicles");
        
    }
    return response.json()
    
}


//fala com o backend
