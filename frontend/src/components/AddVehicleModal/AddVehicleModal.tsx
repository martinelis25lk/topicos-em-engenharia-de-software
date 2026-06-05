import { useEffect, useState } from "react";
import { getCatalogVehicles, createUserVehicle } from "../../api/vehicleApi";
import type { CatalogVehicle } from "../../types/catalog";
import "./AddVehicleModel.css"

interface Props {
  onClose: () => void;
  onSuccess: () => void;
}

export default function AddVehicleModal({ onClose, onSuccess }: Props) {
  const [catalog, setCatalog] = useState<CatalogVehicle[]>([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");
  const [search, setSearch] = useState("");

  const [selected, setSelected] = useState<CatalogVehicle | null>(null);
  const [form, setForm] = useState({
    nickName: "",
    currentHorsePower: "",
    currentTorque: "",
    currentWeight: "",
  });

  useEffect(() => {
    getCatalogVehicles()
      .then(setCatalog)
      .catch(() => setError("Erro ao carregar catálogo."))
      .finally(() => setLoading(false));
  }, []);

  const filtered = catalog.filter((c) =>
    `${c.brand} ${c.model} ${c.year}`.toLowerCase().includes(search.toLowerCase())
  );

  function handleSelect(c: CatalogVehicle) {
    setSelected(c);
    setForm({
      nickName: `${c.brand} ${c.model}`,
      currentHorsePower: String(c.factoryHorsepower ?? ""),
      currentTorque: String(c.factoryTorque ?? ""),
      currentWeight: String(c.factoryWeight ?? ""),
    });
  }

  async function handleSubmit() {
    if (!selected) return;
    if (!form.nickName || !form.currentHorsePower || !form.currentTorque || !form.currentWeight) {
      setError("Preencha todos os campos.");
      return;
    }
    setSubmitting(true);
    setError("");
    try {
      await createUserVehicle({
        vehicleCatalogModelId: selected.id,
        nickName: form.nickName,
        currentHorsePower: Number(form.currentHorsePower),
        currentTorque: Number(form.currentTorque),
        currentWeight: Number(form.currentWeight),
      });
      onSuccess();
      onClose();
    } catch {
      setError("Erro ao adicionar veículo. Tente novamente.");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>Add Vehicle</h2>
          <button className="modal-close" onClick={onClose}>
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
              stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
              <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
            </svg>
          </button>
        </div>

        {!selected ? (
          <>
            <p className="modal-sub">Selecione um modelo do catálogo</p>
            <input
              className="modal-search"
              placeholder="Buscar marca, modelo ou ano..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              autoFocus
            />
            {loading ? (
              <p className="modal-loading">Carregando catálogo...</p>
            ) : (
              <div className="modal-catalog-list">
                {filtered.length === 0 ? (
                  <p className="modal-empty">Nenhum modelo encontrado.</p>
                ) : (
                  filtered.map((c) => (
                    <button key={c.id} className="modal-catalog-item" onClick={() => handleSelect(c)}>
                      <div className="mci-name">{c.brand} {c.model}</div>
                      <div className="mci-meta">{c.engineCode} • {c.year}</div>
                      <div className="mci-specs">
                        <span>{c.factoryHorsepower}hp</span>
                        <span>{c.factoryTorque}lb-ft</span>
                        <span>{c.factoryWeight}kg</span>
                      </div>
                    </button>
                  ))
                )}
              </div>
            )}
          </>
        ) : (
          <>
            <div className="modal-selected-badge">
              <span className="msb-brand">{selected.brand}</span>
              <span className="msb-model">{selected.model} {selected.year}</span>
              <button className="msb-change" onClick={() => setSelected(null)}>Trocar</button>
            </div>

            <div className="modal-form">
              <label>
                <span>Apelido do veículo</span>
                <input
                  value={form.nickName}
                  onChange={(e) => setForm({ ...form, nickName: e.target.value })}
                  placeholder="Ex: Meu R34"
                  maxLength={30}
                />
              </label>
              <label>
                <span>Potência atual (hp)</span>
                <input
                  type="number"
                  value={form.currentHorsePower}
                  onChange={(e) => setForm({ ...form, currentHorsePower: e.target.value })}
                  placeholder={String(selected.factoryHorsepower)}
                />
              </label>
              <label>
                <span>Torque atual (lb-ft)</span>
                <input
                  type="number"
                  value={form.currentTorque}
                  onChange={(e) => setForm({ ...form, currentTorque: e.target.value })}
                  placeholder={String(selected.factoryTorque)}
                />
              </label>
              <label>
                <span>Peso atual (kg)</span>
                <input
                  type="number"
                  value={form.currentWeight}
                  onChange={(e) => setForm({ ...form, currentWeight: e.target.value })}
                  placeholder={String(selected.factoryWeight)}
                />
              </label>
            </div>

            {error && <p className="modal-error">{error}</p>}

            <div className="modal-actions">
              <button className="modal-btn-cancel" onClick={onClose}>Cancelar</button>
              <button className="modal-btn-submit" onClick={handleSubmit} disabled={submitting}>
                {submitting ? "Adicionando..." : "Adicionar à Garagem"}
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}