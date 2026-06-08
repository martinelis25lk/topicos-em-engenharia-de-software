import { useState, useEffect } from "react";

export interface CurrentUser {
  email: string;
  username: string;
  role: "ROLE_USER" | "ROLE_ADMIN" | string;
  profileImageUrl: string | null;
}

function decodeJwtPayload(token: string): Record<string, unknown> | null {
  try {
    const base64 = token.split(".")[1].replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(atob(base64));
  } catch {
    return null;
  }
}

export function useCurrentUser(): CurrentUser | null {
  const [user, setUser] = useState<CurrentUser | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const payload = decodeJwtPayload(token);
    if (!payload) return;

    const email = (payload.sub as string) ?? "";
    const username = (payload.username as string) ?? email.split("@")[0];
    const role = (payload.role as string) ?? "ROLE_USER";
    const profileImageUrl = (payload.profileImageUrl as string) ?? null;

    setUser({ email, username, role, profileImageUrl });
  }, []);

  return user;
}