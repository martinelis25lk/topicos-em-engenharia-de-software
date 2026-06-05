import { useEffect, useState } from "react";
import {
  getCatalogVehicles,
  createCatalogVehicle,
  updateCatalogVehicle,
  deleteCatalogVehicle,
} from "../api/vehicleApi";
import { useCurrentUser } from "../hooks/useCurrentUser";
import type { CatalogVehicle } from "../types/catalog";
import "./CatalogPage.css";

const ASPIRATION_TYPES = ["NATURALLY_ASPIRATED", "TURBOCHARGED", "SUPERCHARGED", "TWIN_TURBO", "HYBRID"];

const emptyForm = {
  brand: "", model: "", year: new Date().getFullYear(),
  engineCode: "", aspirationType: "NATURALLY_ASPIRATED",
  factoryHorsePower: 0, factoryTorque: 0, factoryWeight: 0,
  fipeBrandCode: "", fipeModelCode: "", fipeYearCode: "",
};

export default function CatalogPage() {
  const user = useCurrentUser();
  const isAdmin = user?.role === "ROLE_ADMIN";

  const [vehicles, setVehicles] = useState<CatalogVehicle[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editing, setEditing] = useState<CatalogVehicle | null>(null);
  const [form, setForm] = useState({ ...emptyForm });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");
  const [deletingId, setDeletingId] = useState<number | null>(null);

  async function load() {
    setLoading(true);
    try { setVehicles(await getCatalogVehicles()); }
    catch { setError("Erro ao carregar catálogo."); }
    finally { setLoading(false); }
  }

  useEffect(() => { load(); }, []);

  const filtered = vehicles.filter((v) =>
    `${v.brand} ${v.model} ${v.year} ${v.engineCode}`.toLowerCase().includes(search.toLowerCase())
  );

  function openCreate() {
    setEditing(null);
    setForm({ ...emptyForm });
    setError("");
    setShowForm(true);
  }

  function openEdit(v: CatalogVehicle) {
    setEditing(v);
    setForm({
      brand: v.brand, model: v.model, year: v.year,
      engineCode: v.engineCode, aspirationType: v.aspirationType ?? "NATURALLY_ASPIRATED",
      factoryHorsePower: v.factoryHorsepower ?? 0,
      factoryTorque: v.factoryTorque ?? 0,
      factoryWeight: v.factoryWeight ?? 0,
      fipeBrandCode: v.fipeBrandCode ?? "",
      fipeModelCode: v.fipeModelCode ?? "",
      fipeYearCode: v.fipeYearCode ?? "",
    });
    setError("");
    setShowForm(true);
  }

  async function handleSubmit() {
    if (!form.brand || !form.model || !form.engineCode) {
      setError("Preencha marca, modelo e código do motor.");
      return;
    }
    setSubmitting(true);
    setError("");
    try {
      if (editing) {
        await updateCatalogVehicle(editing.id, form);
      } else {
        await createCatalogVehicle(form);
      }
      setShowForm(false);
      load();
    } catch {
      setError("Erro ao salvar. Verifique os campos.");
    } finally {
      setSubmitting(false);
    }
  }

  async function handleDelete(id: number) {
    if (!confirm("Remover este modelo do catálogo?")) return;
    setDeletingId(id);
    try { await deleteCatalogVehicle(id); load(); }
    catch { alert("Erro ao remover."); }
    finally { setDeletingId(null); }
  }

  function F(key: keyof typeof form, value: string | number) {
    setForm((p) => ({ ...p, [key]: value }));
  }

  return (
    <div className="catalog-page">
      <div className="catalog-header">
        <div>
          <h1>Vehicle Catalog</h1>
          <p>Base de modelos de fábrica</p>
        </div>
        {isAdmin && (
          <button className="catalog-add-btn" onClick={openCreate}>
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none"
              stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
              <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            Add Model
          </button>
        )}
      </div>

      <input
        className="catalog-search"
        placeholder="Buscar por marca, modelo, motor ou ano..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {loading ? (
        <p className="catalog-loading">Carregando...</p>
      ) : (
        <div className="catalog-table-wrap">
          <table className="catalog-table">
            <thead>
              <tr>
                <th>Marca</th>
                <th>Modelo</th>
                <th>Ano</th>
                <th>Motor</th>
                <th>HP</th>
                <th>Torque</th>
                <th>Peso</th>
                {isAdmin && <th>Ações</th>}
              </tr>
            </thead>
            <tbody>
              {filtered.length === 0 ? (
                <tr><td colSpan={8} className="catalog-empty">Nenhum modelo encontrado.</td></tr>
              ) : (
                filtered.map((v) => (
                  <tr key={v.id}>
                    <td>{v.brand}</td>
                    <td className="catalog-model">{v.model}</td>
                    <td>{v.year}</td>
                    <td className="catalog-engine">{v.engineCode}</td>
                    <td className="catalog-value">{v.factoryHorsepower}hp</td>
                    <td className="catalog-value">{v.factoryTorque}lb-ft</td>
                    <td className="catalog-value">{v.factoryWeight}kg</td>
                    {isAdmin && (
                      <td>
                        <div className="catalog-actions">
                          <button className="catalog-btn-edit" onClick={() => openEdit(v)}>
                            <svg width="13" height="13" viewBox="0 0 24 24" fill="none"
                              stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                              <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                              <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                            </svg>
                          </button>
                          <button
                            className="catalog-btn-delete"
                            onClick={() => handleDelete(v.id)}
                            disabled={deletingId === v.id}
                          >
                            <svg width="13" height="13" viewBox="0 0 24 24" fill="none"
                              stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                              <polyline points="3 6 5 6 21 6"/>
                              <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                              <path d="M10 11v6M14 11v6"/>
                            </svg>
                          </button>
                        </div>
                      </td>
                    )}
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* ── Form modal ─────────────────────────────────── */}
      {showForm && (
        <div className="modal-overlay" onClick={() => setShowForm(false)}>
          <div className="modal-box catalog-form-box" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>{editing ? "Editar Modelo" : "Novo Modelo"}</h2>
              <button className="modal-close" onClick={() => setShowForm(false)}>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                  stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                  <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
                </svg>
              </button>
            </div>

            <div className="catalog-form">
              <div className="cf-row">
                <label><span>Marca *</span><input value={form.brand} onChange={(e) => F("brand", e.target.value)} placeholder="Ex: Nissan" /></label>
                <label><span>Modelo *</span><input value={form.model} onChange={(e) => F("model", e.target.value)} placeholder="Ex: Skyline R34" /></label>
              </div>
              <div className="cf-row">
                <label><span>Ano *</span><input type="number" value={form.year} onChange={(e) => F("year", Number(e.target.value))} /></label>
                <label><span>Código do motor *</span><input value={form.engineCode} onChange={(e) => F("engineCode", e.target.value)} placeholder="Ex: RB26DETT" /></label>
              </div>
              <label>
                <span>Aspiração</span>
                <select value={form.aspirationType} onChange={(e) => F("aspirationType", e.target.value)}>
                  {ASPIRATION_TYPES.map((a) => <option key={a} value={a}>{a.replace(/_/g, " ")}</option>)}
                </select>
              </label>
              <div className="cf-row">
                <label><span>HP de fábrica *</span><input type="number" value={form.factoryHorsePower} onChange={(e) => F("factoryHorsePower", Number(e.target.value))} /></label>
                <label><span>Torque de fábrica *</span><input type="number" value={form.factoryTorque} onChange={(e) => F("factoryTorque", Number(e.target.value))} /></label>
                <label><span>Peso de fábrica (kg) *</span><input type="number" value={form.factoryWeight} onChange={(e) => F("factoryWeight", Number(e.target.value))} /></label>
              </div>
              <p className="cf-section">FIPE (opcional)</p>
              <div className="cf-row">
                <label><span>Brand Code</span><input value={form.fipeBrandCode} onChange={(e) => F("fipeBrandCode", e.target.value)} placeholder="Ex: 26" /></label>
                <label><span>Model Code</span><input value={form.fipeModelCode} onChange={(e) => F("fipeModelCode", e.target.value)} placeholder="Ex: 5571" /></label>
                <label><span>Year Code</span><input value={form.fipeYearCode} onChange={(e) => F("fipeYearCode", e.target.value)} placeholder="Ex: 1995-1" /></label>
              </div>
            </div>

            {error && <p className="modal-error" style={{ margin: "0 20px 8px" }}>{error}</p>}

            <div className="modal-actions">
              <button className="modal-btn-cancel" onClick={() => setShowForm(false)}>Cancelar</button>
              <button className="modal-btn-submit" onClick={handleSubmit} disabled={submitting}>
                {submitting ? "Salvando..." : editing ? "Salvar alterações" : "Criar modelo"}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}