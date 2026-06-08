import { useState } from "react";
import { createChronicIssue } from "../../api/chronicIssueApi";
import "./CreateChronicIssueModal.css";

interface Props {
  modelId: number;
  onClose: () => void;
  onSuccess: () => void;
}

const SEVERITIES = ["LOW", "MEDIUM", "HIGH", "CRITICAL"];
const CATEGORIES = ["MOTOR", "SUSPENSION", "COOLING", "TRANSMISSION", "ELECTRICAL", "BODYWORK", "BRAKES", "TURBO", "FUEL_SYSTEM"];
const COMPLEXITIES = ["DIY", "INTERMEDIATE", "PROFESSIONAL"];

export default function CreateChronicIssueModal({ modelId, onClose, onSuccess }: Props) {
  const [form, setForm] = useState({
    title: "", description: "", severity: "MEDIUM", category: "MOTOR",
    millageMin: "", millageMax: "", costMin: "", costMax: "",
    affectedEngines: "", affectedYears: "", repairComplexity: "INTERMEDIATE",
    symptoms: [""], preventiveMaintenance: [""],
  });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  function set(k: string, v: string) { setForm((f) => ({ ...f, [k]: v })); }

  function handleList(key: "symptoms" | "preventiveMaintenance", i: number, val: string) {
    setForm((f) => { const arr = [...f[key]]; arr[i] = val; return { ...f, [key]: arr }; });
  }
  function addItem(key: "symptoms" | "preventiveMaintenance") {
    setForm((f) => ({ ...f, [key]: [...f[key], ""] }));
  }
  function removeItem(key: "symptoms" | "preventiveMaintenance", i: number) {
    setForm((f) => { const arr = f[key].filter((_, idx) => idx !== i); return { ...f, [key]: arr.length ? arr : [""] }; });
  }

  async function handleSubmit() {
    if (!form.title || !form.description) return setError("Título e descrição são obrigatórios.");
    setError(""); setSubmitting(true);
    try {
      await createChronicIssue({
        vehicleCatalogModelId: modelId, title: form.title, description: form.description,
        severity: form.severity, category: form.category,
        millageMin: Number(form.millageMin) || 0, millageMax: Number(form.millageMax) || 0,
        costMin: Number(form.costMin) || 0, costMax: Number(form.costMax) || 0,
        affectedEngines: form.affectedEngines, affectedYears: form.affectedYears,
        repairComplexity: form.repairComplexity, createdByUserId: 0,
        symptoms: form.symptoms.filter(Boolean),
        preventiveMaintenance: form.preventiveMaintenance.filter(Boolean),
      });
      onSuccess();
    } catch (e: any) {
      setError(e?.response?.data?.message ?? "Erro. Verifique se você tem este modelo na garagem.");
    } finally { setSubmitting(false); }
  }

  return (
    <div className="ccim-overlay" onClick={onClose}>
      <div className="ccim-modal" onClick={(e) => e.stopPropagation()}>
        <div className="ccim-header">
          <h3>Report Chronic Issue</h3>
          <button className="ccim-close" onClick={onClose}>✕</button>
        </div>
        <div className="ccim-body">
          <div className="ccim-info-banner">
            ℹ Você precisa ter este modelo na garagem para criar um issue. Issues passam por votação da comunidade antes de serem aprovados.
          </div>
          {error && <p className="ccim-error">{error}</p>}

          <div className="ccim-row">
            <div className="ccim-field">
              <label>Título *</label>
              <input value={form.title} onChange={(e) => set("title", e.target.value)} placeholder="ex: Falha nos anéis do pistão" maxLength={30} />
            </div>
          </div>
          <div className="ccim-field">
            <label>Descrição *</label>
            <textarea value={form.description} onChange={(e) => set("description", e.target.value)} placeholder="Descreva o problema..." maxLength={100} />
          </div>
          <div className="ccim-row ccim-row-3">
            <div className="ccim-field"><label>Severidade</label>
              <select value={form.severity} onChange={(e) => set("severity", e.target.value)}>
                {SEVERITIES.map((s) => <option key={s}>{s}</option>)}
              </select>
            </div>
            <div className="ccim-field"><label>Categoria</label>
              <select value={form.category} onChange={(e) => set("category", e.target.value)}>
                {CATEGORIES.map((c) => <option key={c}>{c}</option>)}
              </select>
            </div>
            <div className="ccim-field"><label>Complexidade</label>
              <select value={form.repairComplexity} onChange={(e) => set("repairComplexity", e.target.value)}>
                {COMPLEXITIES.map((c) => <option key={c}>{c}</option>)}
              </select>
            </div>
          </div>
          <div className="ccim-row">
            <div className="ccim-field"><label>Km Mínima</label>
              <input type="number" value={form.millageMin} onChange={(e) => set("millageMin", e.target.value)} placeholder="ex: 40000" /></div>
            <div className="ccim-field"><label>Km Máxima</label>
              <input type="number" value={form.millageMax} onChange={(e) => set("millageMax", e.target.value)} placeholder="ex: 80000" /></div>
          </div>
          <div className="ccim-row">
            <div className="ccim-field"><label>Custo Mín. (R$)</label>
              <input type="number" value={form.costMin} onChange={(e) => set("costMin", e.target.value)} placeholder="ex: 500" /></div>
            <div className="ccim-field"><label>Custo Máx. (R$)</label>
              <input type="number" value={form.costMax} onChange={(e) => set("costMax", e.target.value)} placeholder="ex: 5000" /></div>
          </div>
          <div className="ccim-row">
            <div className="ccim-field"><label>Motores Afetados</label>
              <input value={form.affectedEngines} onChange={(e) => set("affectedEngines", e.target.value)} placeholder="ex: EJ257, EJ205" /></div>
            <div className="ccim-field"><label>Anos Afetados</label>
              <input value={form.affectedYears} onChange={(e) => set("affectedYears", e.target.value)} placeholder="ex: 2015-2021" /></div>
          </div>
          <div className="ccim-field">
            <label>Sintomas</label>
            {form.symptoms.map((s, i) => (
              <div key={i} className="ccim-list-row">
                <input value={s} onChange={(e) => handleList("symptoms", i, e.target.value)} placeholder={`Sintoma ${i + 1}`} />
                <button className="ccim-list-remove" onClick={() => removeItem("symptoms", i)}>✕</button>
              </div>
            ))}
            <button className="ccim-list-add" onClick={() => addItem("symptoms")}>+ Adicionar sintoma</button>
          </div>
          <div className="ccim-field">
            <label>Manutenção Preventiva</label>
            {form.preventiveMaintenance.map((s, i) => (
              <div key={i} className="ccim-list-row">
                <input value={s} onChange={(e) => handleList("preventiveMaintenance", i, e.target.value)} placeholder={`Dica ${i + 1}`} />
                <button className="ccim-list-remove" onClick={() => removeItem("preventiveMaintenance", i)}>✕</button>
              </div>
            ))}
            <button className="ccim-list-add" onClick={() => addItem("preventiveMaintenance")}>+ Adicionar dica</button>
          </div>
        </div>
        <div className="ccim-footer">
          <button className="ccim-cancel" onClick={onClose}>Cancelar</button>
          <button className="ccim-submit" onClick={handleSubmit} disabled={submitting}>
            {submitting ? "Enviando..." : "Criar Issue"}
          </button>
        </div>
      </div>
    </div>
  );
}