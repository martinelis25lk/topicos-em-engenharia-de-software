import axios from "axios";

const API_URL = "http://localhost:8080";

export const getUserVehicles = async () => {
  const response = await axios.get(`${API_URL}/user-vehicles`);
  return response.data;
};

export const getVehicleById = async (id: number) => {
  const response = await axios.get(`${API_URL}/user-vehicles/${id}`);
  return response.data;
};