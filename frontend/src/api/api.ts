import axios from "axios";

const api = axios.create({
  // Usa a variável do Vite. Se não existir (em dev local), cai no localhost.
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;