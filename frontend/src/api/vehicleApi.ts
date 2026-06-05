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

// ── User Vehicles ──────────────────────────────────────────
export const getUserVehicles = async () => {
  const response = await api.get("/user-vehicles/me");
  return response.data;
};

export const getFeedVehicles = async () => {
  const response = await api.get("/user-vehicles/feed");
  return response.data;
};

export const getVehicleById = async (id: number) => {
  const response = await api.get(`/user-vehicles/${id}`);
  return response.data;
};

export const createUserVehicle = async (data: {
  vehicleCatalogModelId: number;
  nickName: string;
  currentHorsePower: number;
  currentTorque: number;
  currentWeight: number;
}) => {
  const response = await api.post("/user-vehicles", data);
  return response.data;
};

export const deleteUserVehicle = async (id: number) => {
  await api.delete(`/user-vehicles/${id}`);
};

// ── Catalog (ROLE_ADMIN only) ──────────────────────────────
export const getCatalogVehicles = async () => {
  const response = await api.get("/catalog/vehicles");
  return response.data;
};

export const createCatalogVehicle = async (data: {
  brand: string;
  model: string;
  year: number;
  engineCode: string;
  aspirationType: string;
  factoryHorsePower: number;
  factoryTorque: number;
  factoryWeight: number;
  fipeBrandCode?: string;
  fipeModelCode?: string;
  fipeYearCode?: string;
}) => {
  const response = await api.post("/catalog/vehicles", data);
  return response.data;
};

export const updateCatalogVehicle = async (id: number, data: {
  brand: string;
  model: string;
  year: number;
  engineCode: string;
  aspirationType: string;
  factoryHorsePower: number;
  factoryTorque: number;
  factoryWeight: number;
  fipeBrandCode?: string;
  fipeModelCode?: string;
  fipeYearCode?: string;
}) => {
  const response = await api.put(`/catalog/vehicles/${id}`, data);
  return response.data;
};

export const deleteCatalogVehicle = async (id: number) => {
  await api.delete(`/catalog/vehicles/${id}`);
};