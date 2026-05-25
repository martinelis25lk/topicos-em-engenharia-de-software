import axios from "axios";

const API_URL = "http://localhost:8080";

type LoginRequest = {
  username: string;
  password: string;
};

type RegisterRequest = {
  username: string;
  password: string;
};

export const login = async (data: LoginRequest) => {
  const response = await axios.post(`${API_URL}/auth/login`, data);
  return response.data;
};

export const register = async (data: RegisterRequest) => {
  const response = await axios.post(`${API_URL}/auth/register`, data);
  return response.data;
};