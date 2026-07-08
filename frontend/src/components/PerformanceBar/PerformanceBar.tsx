import "./PerformanceBar.css";

interface Props {
  label: string;
  factoryValue: number;
  currentValue: number;
  diff: number;
  trend: "POSITIVE" | "NEGATIVE" | "NEUTRAL";
  unit?: string;
}

export function PerformanceBar({
  label,
  factoryValue,
  currentValue,
  diff,
  trend,
  unit = "",
}: Props) {
  const max = Math.max(factoryValue, currentValue, 1);
  const factoryPct = Math.round((factoryValue / max) * 100);
  const currentPct = Math.round((currentValue / max) * 100);

  const isGain = trend === "POSITIVE";
  const isLoss = trend === "NEGATIVE";

  // Cor do gain/loss
  const gainColor =
    trend === "POSITIVE" ? "#00ffae" :
    trend === "NEGATIVE" ? "#ff4d4d" : "#888";

  const sign = diff > 0 ? "+" : "";
  const diffLabel = diff !== 0 ? `${sign}${diff}${unit}` : "sem alteração";

  return (
    <div className="pb-container">
      {/* Header */}
      <div className="pb-header">
        <span className="pb-label">{label}</span>
        <div className="pb-values">
          <span className="pb-factory">{factoryValue}{unit}</span>
          <span className="pb-arrow">→</span>
          <span className="pb-current" style={{ color: gainColor }}>
            {currentValue}{unit}
          </span>
        </div>
      </div>

      {/* Barra estilo NFS */}
      <div className="pb-track">
        {/* Segmentos */}
        {Array.from({ length: 20 }).map((_, i) => {
          const segPct = (i + 1) * 5; // 5, 10, 15 ... 100
          const inFactory = segPct <= factoryPct;
          const inGain    = isGain && segPct > factoryPct && segPct <= currentPct;
          const inLoss    = isLoss && segPct > currentPct && segPct <= factoryPct;

          let cls = "pb-seg";
          if (inFactory) cls += " pb-seg--factory";
          if (inGain)    cls += " pb-seg--gain";
          if (inLoss)    cls += " pb-seg--loss";

          return <div key={i} className={cls} />;
        })}
      </div>

      {/* Footer */}
      <div className="pb-footer">
        <span className="pb-diff" style={{ color: gainColor }}>
          {diff !== 0 && (isGain ? "▲" : "▼")} {diffLabel}
        </span>
        {diff !== 0 && (
          <span className="pb-pct" style={{ color: gainColor }}>
            {Math.abs(Math.round(((currentValue - factoryValue) / factoryValue) * 100))}%
          </span>
        )}
      </div>
    </div>
  );
}