import axios from "axios";

const api = axios.create({
  // Usa a variável do Vite. Se não existir (em dev local), cai no localhost.
  baseURL: "http://13.222.159.194:8080", // Substitua pelo URL do seu backend",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;