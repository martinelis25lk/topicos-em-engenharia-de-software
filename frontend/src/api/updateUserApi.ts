import axios from "axios";

const API_URL = "http://98.81.151.18:8080";

export interface UpdateUserRequest {
  username?: string;
  fullName?: string;
  email?: string;
}

export const updateMe = async (
  data: UpdateUserRequest
) => {
  const response = await axios.patch(
    `${API_URL}/users/me`,
    data,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  );

  return response.data;
};

export const getMe = async () => {
  const response = await axios.get(`${API_URL}/users/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });

  return response.data;
};

export const uploadProfileImage = async (file: File) => {
  const token = localStorage.getItem("token");

  const formData = new FormData();
  formData.append("file", file);

  const response = await axios.post(
    `${API_URL}/users/me/profile-image`,
    formData,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "multipart/form-data",
      },
    }
  );

  return response.data as string;
};