const API_BASE = "http://localhost:8080";

export async function fetchVehicles() {
  const response = await fetch(`${API_BASE}/vehicles`);
  if (!response.ok) {
    throw new Error("Erro ao buscar veículos");
  }
  return response.json();
}