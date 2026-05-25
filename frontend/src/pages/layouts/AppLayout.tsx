import { NavLink, Outlet, useNavigate } from "react-router-dom";
import "./AppLayout.css";

export default function AppLayout() {
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.removeItem("token");
    navigate("/login");
  }

  return (
    <div className="app-layout">
      <aside className="sidebar">
        <h1>LasanhaSpec</h1>
        <p>Automotive Enthusiast Platform</p>

        <nav>
          <NavLink to="/">Feed</NavLink>
          <NavLink to="/garage">Garage</NavLink>
          <NavLink to="/chronic-issues">Chronic Issues</NavLink>
          <NavLink to="/explore">Explore</NavLink>
        </nav>
      </aside>

      <div className="app-main">
        <header className="topbar">
          <input placeholder="Buscar veículos, builds, crônicos..." />
          <button onClick={handleLogout}>Sair</button>
        </header>

        <main className="content">
          <Outlet />
        </main>
      </div>
    </div>
  );
}