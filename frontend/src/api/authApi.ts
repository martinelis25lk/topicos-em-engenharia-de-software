import axios from "axios";

const API_URL = "http://98.81.151.18:8080";

type LoginRequest = {
  email: string;
  password: string;
};

type RegisterRequest = {
  username: string;
  email: string;
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