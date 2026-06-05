import { useState } from "react";
import { useUserVehicles } from "../hooks/useUserVehicles";
import VehicleCard from "../components/VehicleCard/VehicleCard";
import AddVehicleModal from "../components/AddVehicleModal/AddVehicleModal";
import { deleteUserVehicle } from "../api/vehicleApi";
import "./GaragePage.css";

const GaragePage = () => {
  const { vehicles, loading, refetch } = useUserVehicles();
  const [showModal, setShowModal] = useState(false);
  const [deletingId, setDeletingId] = useState<number | null>(null);

  async function handleDelete(id: number) {
    if (!confirm("Remover este veículo da garagem?")) return;
    setDeletingId(id);
    try {
      await deleteUserVehicle(id);
      refetch();
    } catch {
      alert("Erro ao remover veículo.");
    } finally {
      setDeletingId(null);
    }
  }

  if (loading) return <p style={{ color: "#888", padding: 24 }}>Carregando...</p>;

  return (
    <div className="garage-page">
      <div className="garage-header">
        <div className="garage-header-text">
          <h1>My Garage</h1>
          <p>Manage your vehicle builds</p>
        </div>
        <button className="add-vehicle-btn" onClick={() => setShowModal(true)}>
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          Add Vehicle
        </button>
      </div>

      <div className="garage-list">
        {vehicles.length === 0 ? (
          <div className="garage-empty">
            <p>Nenhum veículo ainda. Adicione o primeiro!</p>
          </div>
        ) : (
          vehicles.map((vehicle) => (
            <div key={vehicle.id} className="garage-item">
              <VehicleCard vehicle={vehicle} />
              <button
                className="garage-delete-btn"
                onClick={() => handleDelete(vehicle.id)}
                disabled={deletingId === vehicle.id}
                title="Remover veículo"
              >
                {deletingId === vehicle.id ? "..." : (
                  <svg width="15" height="15" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
                    <path d="M10 11v6M14 11v6"/>
                    <path d="M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"/>
                  </svg>
                )}
              </button>
            </div>
          ))
        )}
      </div>

      {showModal && (
        <AddVehicleModal
          onClose={() => setShowModal(false)}
          onSuccess={refetch}
        />
      )}
    </div>
  );
};

export default GaragePage;