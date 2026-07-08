import { useEffect, useState } from "react";
import type {
  CatalogVehicle,
  CatalogVehicleForm,
  AspirationType,
  FuelType,
  DriveType,
  TransmissionType,
  TransmissionModel,
} from "../../types/catalog";
import "./CatalogFormModal.css";

interface Props {
  editing: CatalogVehicle | null;
  onClose: () => void;
  onSubmit: (form: CatalogVehicleForm) => Promise<void>;
}

const ASPIRATION: AspirationType[] = ["NATURALLY_ASPIRATED", "TURBOCHARGED", "SUPERCHARGED"];
const FUEL: FuelType[] = ["GASOLINE", "ETHANOL", "FLEX", "DIESEL", "HYBRID", "ELECTRIC"];
const DRIVE: DriveType[] = ["RWD", "FWD", "AWD"];
const TRANSMISSION_TYPE: TransmissionType[] = ["MANUAL", "AUTOMATIC", "AUTOMATED_MANUAL", "DUAL_CLUTCH", "CVT"];
const TRANSMISSION_MODEL: TransmissionModel[] = [
  "MANUAL_4", "MANUAL_5", "MANUAL_6",
  "AL4", "AISIN_AT6", "ZF_8HP",
  "DUALOGIC", "I_MOTION", "EASYTRONIC",
  "DSG", "POWERSHIFT",
  "CVT_JATCO", "CVT_HONDA",
];

const EMPTY: CatalogVehicleForm = {
  brand: "", model: "", year: new Date().getFullYear(),
  engineCode: "", aspirationType: "NATURALLY_ASPIRATED",
  factoryHorsePower: 0, factoryTorque: 0, factoryWeight: 0,
  displacement: 0, cylinderCount: 4,
  topSpeed: 0, acceleration0to100: 0,
  fuelType: "GASOLINE", driveType: "RWD",
  transmissionType: "MANUAL", transmissionModel: "MANUAL_6",
  gearCount: 6,
  fipeBrandCode: "", fipeModelCode: "", fipeYearCode: "",
};

type Tab = "basic" | "performance" | "transmission" | "fipe";

export default function CatalogFormModal({ editing, onClose, onSubmit }: Props) {
  const [form, setForm] = useState<CatalogVehicleForm>({ ...EMPTY });
  const [tab, setTab] = useState<Tab>("basic");
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (editing) {
      setForm({
        brand: editing.brand,
        model: editing.model,
        year: editing.year,
        engineCode: editing.engineCode,
        aspirationType: editing.aspirationType,
        factoryHorsePower: editing.factoryHorsepower,
        factoryTorque: editing.factoryTorque,
        factoryWeight: editing.factoryWeight,
        displacement: editing.displacement ?? 0,
        cylinderCount: editing.cylinderCount ?? 4,
        topSpeed: editing.topSpeed ?? 0,
        acceleration0to100: editing.acceleration0to100 ?? 0,
        fuelType: editing.fuelType ?? "GASOLINE",
        driveType: editing.driveType ?? "RWD",
        transmissionType: editing.transmissionType ?? "MANUAL",
        transmissionModel: editing.transmissionModel ?? "MANUAL_6",
        gearCount: editing.gearCount ?? 6,
        fipeBrandCode: editing.fipeBrandCode ?? "",
        fipeModelCode: editing.fipeModelCode ?? "",
        fipeYearCode: editing.fipeYearCode ?? "",
      });
    } else {
      setForm({ ...EMPTY });
    }
    setTab("basic");
    setError("");
  }, [editing]);

  function set<K extends keyof CatalogVehicleForm>(key: K, value: CatalogVehicleForm[K]) {
    setForm((p) => ({ ...p, [key]: value }));
  }

  function validate(): string {
    if (!form.brand.trim()) return "Marca é obrigatória.";
    if (!form.model.trim()) return "Modelo é obrigatório.";
    if (!form.engineCode.trim()) return "Código do motor é obrigatório.";
    if (form.factoryHorsePower <= 0) return "HP de fábrica deve ser positivo.";
    if (form.factoryTorque <= 0) return "Torque de fábrica deve ser positivo.";
    if (form.factoryWeight <= 0) return "Peso de fábrica deve ser positivo.";
    if (form.year < 1886 || form.year > 2035) return "Ano inválido.";
    return "";
  }

  async function handleSubmit() {
    const err = validate();
    if (err) { setError(err); return; }
    setSubmitting(true);
    setError("");
    try {
      await onSubmit(form);
    } catch {
      setError("Erro ao salvar. Verifique os campos e tente novamente.");
    } finally {
      setSubmitting(false);
    }
  }

  const tabs: { key: Tab; label: string }[] = [
    { key: "basic", label: "Identificação" },
    { key: "performance", label: "Performance" },
    { key: "transmission", label: "Transmissão" },
    { key: "fipe", label: "FIPE" },
  ];

  return (
    <div className="cfm-overlay" onClick={onClose}>
      <div className="cfm-box" onClick={(e) => e.stopPropagation()}>

        {/* ── Header ────────────────────────────────────── */}
        <div className="cfm-header">
          <div>
            <h2>{editing ? "Editar Modelo" : "Novo Modelo"}</h2>
            <p>{editing ? `${editing.brand} ${editing.model} ${editing.year}` : "Preencha os dados do veículo"}</p>
          </div>
          <button className="cfm-close" onClick={onClose}>
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
              stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
              <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
            </svg>
          </button>
        </div>

        {/* ── Tabs ──────────────────────────────────────── */}
        <div className="cfm-tabs">
          {tabs.map((t) => (
            <button
              key={t.key}
              className={`cfm-tab ${tab === t.key ? "cfm-tab--active" : ""}`}
              onClick={() => setTab(t.key)}
            >
              {t.label}
            </button>
          ))}
        </div>

        {/* ── Conteúdo ──────────────────────────────────── */}
        <div className="cfm-body">

          {/* Tab: Identificação */}
          {tab === "basic" && (
            <div className="cfm-section">
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>Marca *</label>
                  <input value={form.brand} onChange={(e) => set("brand", e.target.value)} placeholder="Ex: Nissan" />
                </div>
                <div className="cfm-field">
                  <label>Modelo *</label>
                  <input value={form.model} onChange={(e) => set("model", e.target.value)} placeholder="Ex: Skyline GT-R" />
                </div>
              </div>
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>Ano *</label>
                  <input type="number" value={form.year}
                    onChange={(e) => set("year", Number(e.target.value))}
                    min={1886} max={2035} />
                </div>
                <div className="cfm-field">
                  <label>Código do Motor *</label>
                  <input value={form.engineCode} onChange={(e) => set("engineCode", e.target.value)} placeholder="Ex: RB26DETT" />
                </div>
              </div>
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>Aspiração</label>
                  <select value={form.aspirationType} onChange={(e) => set("aspirationType", e.target.value as AspirationType)}>
                    {ASPIRATION.map((a) => <option key={a} value={a}>{a.replace(/_/g, " ")}</option>)}
                  </select>
                </div>
                <div className="cfm-field">
                  <label>Combustível</label>
                  <select value={form.fuelType} onChange={(e) => set("fuelType", e.target.value as FuelType)}>
                    {FUEL.map((f) => <option key={f} value={f}>{f}</option>)}
                  </select>
                </div>
              </div>
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>Cilindrada (L)</label>
                  <input type="number" step="0.1" value={form.displacement}
                    onChange={(e) => set("displacement", Number(e.target.value))} placeholder="Ex: 2.6" />
                </div>
                <div className="cfm-field">
                  <label>Nº de cilindros</label>
                  <input type="number" value={form.cylinderCount}
                    onChange={(e) => set("cylinderCount", Number(e.target.value))} />
                </div>
              </div>
            </div>
          )}

          {/* Tab: Performance */}
          {tab === "performance" && (
            <div className="cfm-section">
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>HP de fábrica *</label>
                  <div className="cfm-input-unit">
                    <input type="number" value={form.factoryHorsePower}
                      onChange={(e) => set("factoryHorsePower", Number(e.target.value))} />
                    <span>hp</span>
                  </div>
                </div>
                <div className="cfm-field">
                  <label>Torque de fábrica *</label>
                  <div className="cfm-input-unit">
                    <input type="number" value={form.factoryTorque}
                      onChange={(e) => set("factoryTorque", Number(e.target.value))} />
                    <span>Nm</span>
                  </div>
                </div>
              </div>
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>Peso de fábrica *</label>
                  <div className="cfm-input-unit">
                    <input type="number" value={form.factoryWeight}
                      onChange={(e) => set("factoryWeight", Number(e.target.value))} />
                    <span>kg</span>
                  </div>
                </div>
                <div className="cfm-field">
                  <label>Velocidade máxima</label>
                  <div className="cfm-input-unit">
                    <input type="number" value={form.topSpeed}
                      onChange={(e) => set("topSpeed", Number(e.target.value))} />
                    <span>km/h</span>
                  </div>
                </div>
              </div>
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>0–100 km/h</label>
                  <div className="cfm-input-unit">
                    <input type="number" step="0.1" value={form.acceleration0to100}
                      onChange={(e) => set("acceleration0to100", Number(e.target.value))} />
                    <span>s</span>
                  </div>
                </div>
                <div className="cfm-field">
                  <label>Tração</label>
                  <select value={form.driveType} onChange={(e) => set("driveType", e.target.value as DriveType)}>
                    {DRIVE.map((d) => <option key={d} value={d}>{d}</option>)}
                  </select>
                </div>
              </div>

              {/* Preview de métricas */}
              {form.factoryHorsePower > 0 && form.factoryWeight > 0 && (
                <div className="cfm-metrics">
                  <div className="cfm-metric">
                    <span className="cfm-metric-value">
                      {(form.factoryHorsePower / (form.factoryWeight / 1000)).toFixed(1)}
                    </span>
                    <span className="cfm-metric-label">hp/ton</span>
                  </div>
                  <div className="cfm-metric">
                    <span className="cfm-metric-value">
                      {(form.factoryWeight / form.factoryHorsePower).toFixed(2)}
                    </span>
                    <span className="cfm-metric-label">kg/hp</span>
                  </div>
                </div>
              )}
            </div>
          )}

          {/* Tab: Transmissão */}
          {tab === "transmission" && (
            <div className="cfm-section">
              <div className="cfm-row">
                <div className="cfm-field">
                  <label>Tipo de câmbio</label>
                  <select value={form.transmissionType}
                    onChange={(e) => set("transmissionType", e.target.value as TransmissionType)}>
                    {TRANSMISSION_TYPE.map((t) => <option key={t} value={t}>{t.replace(/_/g, " ")}</option>)}
                  </select>
                </div>
                <div className="cfm-field">
                  <label>Modelo do câmbio</label>
                  <select value={form.transmissionModel}
                    onChange={(e) => set("transmissionModel", e.target.value as TransmissionModel)}>
                    {TRANSMISSION_MODEL.map((m) => <option key={m} value={m}>{m.replace(/_/g, " ")}</option>)}
                  </select>
                </div>
              </div>
              <div className="cfm-field" style={{ maxWidth: "50%" }}>
                <label>Número de marchas</label>
                <input type="number" value={form.gearCount}
                  onChange={(e) => set("gearCount", Number(e.target.value))}
                  min={1} max={10} />
              </div>
            </div>
          )}

          {/* Tab: FIPE */}
          {tab === "fipe" && (
            <div className="cfm-section">
              <p className="cfm-fipe-info">
                Códigos FIPE são opcionais e permitem consultar o preço de mercado pelo market-service.
              </p>
              <div className="cfm-field">
                <label>Brand Code</label>
                <input value={form.fipeBrandCode}
                  onChange={(e) => set("fipeBrandCode", e.target.value)}
                  placeholder='Ex: "26" para BMW' />
              </div>
              <div className="cfm-field">
                <label>Model Code</label>
                <input value={form.fipeModelCode}
                  onChange={(e) => set("fipeModelCode", e.target.value)}
                  placeholder='Ex: "5571"' />
              </div>
              <div className="cfm-field">
                <label>Year Code</label>
                <input value={form.fipeYearCode}
                  onChange={(e) => set("fipeYearCode", e.target.value)}
                  placeholder='Ex: "1995-1"' />
              </div>
            </div>
          )}
        </div>

        {/* ── Footer ────────────────────────────────────── */}
        {error && <p className="cfm-error">{error}</p>}

        <div className="cfm-footer">
          <div className="cfm-tab-nav">
            {tab !== "basic" && (
              <button className="cfm-btn-prev" onClick={() => {
                const idx = tabs.findIndex(t => t.key === tab);
                setTab(tabs[idx - 1].key);
              }}>← Anterior</button>
            )}
          </div>
          <div className="cfm-footer-right">
            <button className="cfm-btn-cancel" onClick={onClose}>Cancelar</button>
            {tab !== "fipe" ? (
              <button className="cfm-btn-next" onClick={() => {
                const idx = tabs.findIndex(t => t.key === tab);
                setTab(tabs[idx + 1].key);
              }}>Próximo →</button>
            ) : (
              <button className="cfm-btn-submit" onClick={handleSubmit} disabled={submitting}>
                {submitting ? "Salvando..." : editing ? "Salvar alterações" : "Criar modelo"}
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}