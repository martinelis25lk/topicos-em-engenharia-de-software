import { useRef, useState } from "react";
import { useCurrentUser } from "../hooks/useCurrentUser";
import { uploadProfileImage } from "../api/chronicIssueApi";
import "./ProfilePage.css";

export default function ProfilePage() {
  const user = useCurrentUser();
  const fileRef = useRef<HTMLInputElement>(null);
  const [uploading, setUploading] = useState(false);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);

  async function handleImageUpload(e: React.ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0];
    if (!file) return;
    setPreviewUrl(URL.createObjectURL(file));
    setUploading(true);
    try {
      await uploadProfileImage(file);
    } catch {
      alert("Erro ao fazer upload da imagem de perfil.");
      setPreviewUrl(null);
    } finally {
      setUploading(false);
      if (fileRef.current) fileRef.current.value = "";
    }
  }

  const avatarUrl = previewUrl ?? user?.profileImageUrl ?? null;

  return (
    <div className="profile-page">
      <div className="profile-hero">
        <div className="profile-banner" />
        <div className="profile-info">
          <div className="profile-avatar-wrap">
            <div className="profile-avatar">
              {avatarUrl ? (
                <img src={avatarUrl} alt={user?.username} />
              ) : (
                <div className="profile-avatar-initials">
                  {user?.username?.slice(0, 2).toUpperCase() ?? "??"}
                </div>
              )}
              {uploading && (
                <div className="profile-avatar-loading">
                  <div className="profile-avatar-spinner" />
                </div>
              )}
            </div>
            <button
              className="profile-avatar-upload-btn"
              onClick={() => fileRef.current?.click()}
              title="Trocar foto de perfil"
              disabled={uploading}
            >
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none"
                stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/>
                <polyline points="17 8 12 3 7 8"/>
                <line x1="12" y1="3" x2="12" y2="15"/>
              </svg>
            </button>
            <input
              ref={fileRef}
              type="file"
              accept="image/*"
              style={{ display: "none" }}
              onChange={handleImageUpload}
            />
          </div>

          <div className="profile-meta">
            <div className="profile-name-row">
              <h1>{user?.username ?? "—"}</h1>
              <span className={`profile-badge ${user?.role === "ROLE_ADMIN" ? "profile-badge--admin" : ""}`}>
                {user?.role === "ROLE_ADMIN" ? "Admin" : "Member"}
              </span>
            </div>
            <p className="profile-sub">{user?.email ?? ""}</p>
          </div>
        </div>

        <div className="profile-stats">
          {[
            { value: "—", label: "Total Builds" },
            { value: "—", label: "Approved Contributions" },
            { value: "—", label: "Helpful Votes Received" },
            { value: "—", label: "Confirmed Occurrences" },
          ].map((s) => (
            <div key={s.label} className="profile-stat">
              <span className="profile-stat-value">{s.value}</span>
              <span className="profile-stat-label">{s.label}</span>
            </div>
          ))}
        </div>
      </div>

      <div className="profile-tabs">
        <button className="profile-tab profile-tab--active">Garage</button>
        <button className="profile-tab">Contributions</button>
        <button className="profile-tab">Activity</button>
      </div>

      <div className="profile-wip">
        <p>Em construção — conteúdo das abas em breve.</p>
      </div>
    </div>
  );
}