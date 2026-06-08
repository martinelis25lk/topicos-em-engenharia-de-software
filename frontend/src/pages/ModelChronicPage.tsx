import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  getModelChronicPage, voteOnIssue, approveIssue, rejectIssue,
  type VehicleChronicPage
} from "../api/chronicIssueApi";
import { useCurrentUser } from "../hooks/useCurrentUser";
import CreateChronicIssueModal from "../components/ChronicIssue/CreateChronicIssueModal";
import "./ModelChronicPage.css";

const sevClass: Record<string, string> = {
  LOW: "sev-low", MEDIUM: "sev-medium", HIGH: "sev-high", CRITICAL: "sev-critical",
};

export default function ModelChronicPage() {
  const { id } = useParams<{ id: string }>();
  const modelId = Number(id);
  const navigate = useNavigate();
  const user = useCurrentUser();
  const isAdmin = user?.role === "ROLE_ADMIN";

  const [page, setPage] = useState<VehicleChronicPage | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showCreate, setShowCreate] = useState(false);
  const [votingId, setVotingId] = useState<number | null>(null);
  const [approvingId, setApprovingId] = useState<number | null>(null);

  function load() {
    setLoading(true);
    getModelChronicPage(modelId)
      .then(setPage)
      .catch(() => setError("Erro ao carregar dados do modelo."))
      .finally(() => setLoading(false));
  }

  useEffect(() => { load(); }, [modelId]);

  async function handleVote(issueId: number, type: "USEFUL" | "NOT_USEFUL") {
    setVotingId(issueId);
    try { await voteOnIssue(issueId, type); load(); }
    catch (e: any) { alert(e?.response?.data?.message ?? "Erro ao votar."); }
    finally { setVotingId(null); }
  }

  async function handleApprove(issueId: number) {
    setApprovingId(issueId);
    try { await approveIssue(issueId); load(); }
    catch { alert("Erro ao aprovar."); }
    finally { setApprovingId(null); }
  }

  async function handleReject(issueId: number) {
    if (!confirm("Rejeitar este issue?")) return;
    setApprovingId(issueId);
    try { await rejectIssue(issueId); load(); }
    catch { alert("Erro ao rejeitar."); }
    finally { setApprovingId(null); }
  }

  if (loading) return <div className="mcp-loading"><div className="mcp-spinner" /><p>Carregando...</p></div>;
  if (error || !page) return <p className="mcp-error">{error || "Modelo não encontrado."}</p>;

  return (
    <div className="mcp-page">
      <button className="mcp-back" onClick={() => navigate("/chronic-issues")}>
        ← Back to Chronic Issues
      </button>

      <div className="mcp-hero">
        {page.imgUrl && (
          <div className="mcp-hero-img"><img src={page.imgUrl} alt={page.name} /></div>
        )}
        <div className="mcp-hero-info">
          <h1>{page.name}</h1>
          <p className="mcp-hero-sub">Production Years: {page.year}</p>
          <div className="mcp-hero-stats">
            <div className="mcp-hstat">
              <span className="mcp-hstat-label">Reliability Score</span>
              <span className="mcp-hstat-val" style={{ color: "#f59e0b" }}>
                {page.reliabilityScore?.toFixed(1)}/10
              </span>
            </div>
            <div className="mcp-hstat">
              <span className="mcp-hstat-label">Avg. Annual Cost</span>
              <span className="mcp-hstat-val">${page.avgAnnualCost?.toFixed(0)}</span>
            </div>
            <div className="mcp-hstat">
              <span className="mcp-hstat-label">Critical Failures</span>
              <span className="mcp-hstat-val" style={{ color: "#ef4444" }}>
                {page.criticalFailureRate?.toFixed(0)}%
              </span>
            </div>
            <div className="mcp-hstat">
              <span className="mcp-hstat-label">Documented Issues</span>
              <span className="mcp-hstat-val">{page.documentedIssues}</span>
            </div>
          </div>
        </div>
      </div>

      <div className="mcp-section-header">
        <h2>⚠ Chronic Issues</h2>
        <button className="mcp-report-btn" onClick={() => setShowCreate(true)}>
          + Report Issue
        </button>
      </div>

      {page.issues.length === 0 ? (
        <div className="mcp-empty"><p>Nenhum issue aprovado para este modelo ainda.</p></div>
      ) : (
        <div className="mcp-issues">
          {page.issues.map((issue) => {
            const total = (issue.usefulVotes ?? 0) + (issue.notUsefulVotes ?? 0);
            const pct = total > 0 ? Math.round((issue.usefulVotes / total) * 100) : 0;
            return (
              <div key={issue.id} className="ic-card">
                <div className="ic-top">
                  <span className={`ic-sev ${sevClass[issue.severity] ?? ""}`}>{issue.severity}</span>
                  <h3 className="ic-title" onClick={() => navigate(`/chronic-issues/${issue.id}`)}>
                    {issue.title}
                  </h3>
                </div>
                <p className="ic-desc">{issue.description}</p>
                <div className="ic-meta">
                  <div className="ic-meta-item">
                    <span className="ic-meta-label">Mileage Range</span>
                    <span className="ic-meta-val">{issue.millageMin?.toLocaleString()} – {issue.millageMax?.toLocaleString()} mi</span>
                  </div>
                  <div className="ic-meta-item">
                    <span className="ic-meta-label">Repair Cost</span>
                    <span className="ic-meta-val">${issue.costMin?.toLocaleString()} – ${issue.costMax?.toLocaleString()}</span>
                  </div>
                  <div className="ic-meta-item">
                    <span className="ic-meta-label">Confirmed Cases</span>
                    <span className="ic-meta-val" style={{ color: "#00ffae" }}>{issue.occurrences ?? 0}</span>
                  </div>
                  <div className="ic-meta-item">
                    <span className="ic-meta-label">Usefulness</span>
                    <span className="ic-meta-val">{pct}% helpful</span>
                  </div>
                </div>
                <div className="ic-footer">
                  <div className="ic-votes">
                    <button className="ic-vote-btn ic-vote-useful"
                      onClick={() => handleVote(issue.id, "USEFUL")} disabled={votingId === issue.id}>
                      👍 Useful ({issue.usefulVotes ?? 0})
                    </button>
                    <button className="ic-vote-btn ic-vote-not"
                      onClick={() => handleVote(issue.id, "NOT_USEFUL")} disabled={votingId === issue.id}>
                      👎 Not Useful ({issue.notUsefulVotes ?? 0})
                    </button>
                  </div>
                  <div className="ic-actions">
                    {isAdmin && (
                      <>
                        <button className="ic-approve-btn" onClick={() => handleApprove(issue.id)} disabled={approvingId === issue.id}>✓ Approve</button>
                        <button className="ic-reject-btn" onClick={() => handleReject(issue.id)} disabled={approvingId === issue.id}>✗ Reject</button>
                      </>
                    )}
                    <button className="ic-detail-btn" onClick={() => navigate(`/chronic-issues/${issue.id}`)}>
                      Ver detalhes →
                    </button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {showCreate && (
        <CreateChronicIssueModal
          modelId={modelId}
          onClose={() => setShowCreate(false)}
          onSuccess={() => { setShowCreate(false); load(); }}
        />
      )}
    </div>
  );
}