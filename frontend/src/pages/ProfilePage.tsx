import { useCurrentUser } from "../hooks/useCurrentUser";

export default function ProfilePage() {
  const user = useCurrentUser();

  return (
    <div className="profile-page">
      {/* ── Banner + Info ──────────────────────────────── */}
      <div className="profile-hero">
        <div className="profile-banner" />
        <div className="profile-info">
          <div className="profile-avatar">
            {user?.profileImageUrl ? (
              <img src={user.profileImageUrl} alt={user.username} />
            ) : (
              <div className="profile-avatar-initials">
                {user?.username?.slice(0, 2).toUpperCase() ?? "??"}
              </div>
            )}
          </div>
          <div className="profile-meta">
            <div className="profile-name-row">
              <h1>{user?.username ?? "—"}</h1>
              <span className="profile-badge">Pro Member</span>
            </div>
            <p className="profile-sub">Member since 2024</p>
          </div>
        </div>

        {/* Stats */}
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

      {/* ── Tabs ────────────────────────────────────────── */}
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