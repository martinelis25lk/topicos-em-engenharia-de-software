import api from "./api";

import axios from "axios";

const API_URL = "http://localhost:8080";

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");

  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const getUserVehicles = async () => {
  const response = await api.get("/user-vehicles");
  return response.data;
};


export const getVehicleById = async (id: number) => {
  const response = await api.get(`/user-vehicles/${id}`);
  return response.data;
};