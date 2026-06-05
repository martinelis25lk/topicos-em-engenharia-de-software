import { useNavigate } from "react-router-dom";
import type { VehicleCard as VehicleCardType } from "../../types/vehicle";
import { PerformanceBar } from "../PerformanceBar/PerformanceBar";
import "./VehicleCard.css";

interface Props {
  vehicle: VehicleCardType;
}

const VehicleCard = ({ vehicle }: Props) => {
  const navigate = useNavigate();

  const pwrFactory =
    vehicle.factoryWeight > 0
      ? Math.round((vehicle.factoryHorsePower / vehicle.factoryWeight) * 1000 * 100) / 100
      : 0;

  const pwrCurrent =
    vehicle.currentWeight > 0
      ? Math.round((vehicle.currentHorsePower / vehicle.currentWeight) * 1000 * 100) / 100
      : 0;

  const pwrDiff = Math.round((pwrCurrent - pwrFactory) * 100) / 100;
  const pwrTrend = pwrDiff > 0 ? "POSITIVE" : pwrDiff < 0 ? "NEGATIVE" : "NEUTRAL";

  return (
    <div className="vc-card" onClick={() => navigate(`/vehicle/${vehicle.id}`)}>
      {/* ── Imagem ────────────────────────────────────────── */}
      <div className="vc-image">
        {vehicle.imageUrl ? (
          <img src={vehicle.imageUrl} alt={vehicle.name} />
        ) : (
          <div className="vc-image-placeholder">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none"
              stroke="#333" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
              <rect x="2" y="7" width="20" height="14" rx="2"/>
              <path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/>
              <circle cx="12" cy="13" r="2"/>
            </svg>
          </div>
        )}

        {/* Owner badge sobre a imagem */}
        <div className="vc-owner-badge">
          <span className="vc-owner-label">Owner</span>
          <span className="vc-owner-name">{vehicle.ownerUsername ?? "You"}</span>
        </div>
      </div>

      {/* ── Conteúdo ──────────────────────────────────────── */}
      <div className="vc-content">
        <div className="vc-title-row">
          <div>
            <h2 className="vc-name">{vehicle.name}</h2>
            <p className="vc-engine">{vehicle.engine}</p>
          </div>
          {vehicle.modCount != null && (
            <span className="vc-mods-badge">{vehicle.modCount} mods</span>
          )}
        </div>

        <div className="vc-bars">
          <PerformanceBar
            label="Horsepower"
            factoryValue={vehicle.factoryHorsePower}
            currentValue={vehicle.currentHorsePower}
            diff={vehicle.horsepowerDiff}
            trend={vehicle.horsepowerTrend}
            unit="hp"
          />
          <PerformanceBar
            label="Torque"
            factoryValue={vehicle.factoryTorque}
            currentValue={vehicle.currentTorque}
            diff={vehicle.torqueDiff}
            trend={vehicle.torqueTrend}
            unit="lb-ft"
          />
          <PerformanceBar
            label="Weight"
            factoryValue={vehicle.factoryWeight}
            currentValue={vehicle.currentWeight}
            diff={vehicle.weightDiff}
            trend={vehicle.weightTrend}
            unit="kg"
          />
          <PerformanceBar
            label="Power-to-Weight Ratio"
            factoryValue={pwrFactory}
            currentValue={pwrCurrent}
            diff={pwrDiff}
            trend={pwrTrend}
            unit="hp/ton"
          />
        </div>
      </div>
    </div>
  );
};

export default VehicleCard;