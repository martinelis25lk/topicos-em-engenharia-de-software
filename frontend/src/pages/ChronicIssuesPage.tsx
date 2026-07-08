import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllModelsWithIssues, type VehicleChronicSummary } from "../api/chronicIssueApi";
import "./ChronicIssuesPage.css";

export default function ChronicIssuesPage() {
  const navigate = useNavigate();
  const [models, setModels] = useState<VehicleChronicSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [search, setSearch] = useState("");

  useEffect(() => {
    getAllModelsWithIssues()
      .then(setModels)
      .catch(() => setError("Erro ao carregar modelos."))
      .finally(() => setLoading(false));
  }, []);

  const filtered = models.filter((m) =>
    m.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="ci-page">
      <div className="ci-header">
        <h1>⚠ Chronic Issues</h1>
        <p>Problemas crônicos documentados e validados pela comunidade</p>
      </div>

      <div className="ci-search-row">
        <div className="ci-search-wrap">
          <svg width="15" height="15" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
          <input
            className="ci-search"
            placeholder="Buscar modelo..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>
        <span className="ci-count">{filtered.length} modelo{filtered.length !== 1 ? "s" : ""}</span>
      </div>

      {error && <p className="ci-error">{error}</p>}

      {loading ? (
        <div className="ci-loading"><div className="ci-spinner" /><p>Carregando...</p></div>
      ) : filtered.length === 0 ? (
        <div className="ci-empty">
          <p>Nenhum modelo com issues aprovados encontrado.</p>
          <span>Issues precisam ser aprovados por admins para aparecer aqui.</span>
        </div>
      ) : (
        <div className="ci-grid">
          {filtered.map((model) => (
            <div key={model.id} className="ci-card"
              onClick={() => navigate(`/chronic-issues/models/${model.id}`)}>
              <div className="ci-card-image">
                {model.imageUrl
                  ? <img src={model.imageUrl} alt={model.name} />
                  : <div className="ci-card-img-placeholder" />}
              </div>
              <div className="ci-card-body">
                <h2 className="ci-card-name">{model.name}</h2>
                <div className="ci-card-stats">
                  <div className="ci-stat">
                    <span className="ci-stat-val" style={{ color: "#f59e0b" }}>
                      {model.reliabilityScore?.toFixed(1)}/10
                    </span>
                    <span className="ci-stat-label">Reliability</span>
                  </div>
                  <div className="ci-stat">
                    <span className="ci-stat-val">${model.avgAnnualCost?.toFixed(0)}</span>
                    <span className="ci-stat-label">Avg. Annual Cost</span>
                  </div>
                  <div className="ci-stat">
                    <span className="ci-stat-val" style={{ color: "#ef4444" }}>
                      {model.criticalFailureRate?.toFixed(0)}%
                    </span>
                    <span className="ci-stat-label">Critical Failures</span>
                  </div>
                </div>
                {model.topIssueNames?.length > 0 && (
                  <div className="ci-card-tags">
                    {model.topIssueNames.map((name) => (
                      <span key={name} className="ci-tag">{name}</span>
                    ))}
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}