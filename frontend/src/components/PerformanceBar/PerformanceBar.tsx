import "./PerformanceBar.css";

interface Props {
  label: string;
  factoryValue: number;
  currentValue: number;
  diff: number;
  trend: "POSITIVE" | "NEGATIVE" | "NEUTRAL";
  unit?: string;
}

export function PerformanceBar({ label, factoryValue, currentValue, diff, trend, unit = "" }: Props) {
  const max = Math.max(factoryValue, currentValue) || 1;
  const baseWidth = (factoryValue / max) * 100;
  const currentWidth = (currentValue / max) * 100;
  const diffWidth = Math.abs(currentWidth - baseWidth);
  const start = Math.min(baseWidth, currentWidth);

  const color =
    trend === "POSITIVE" ? "#00ffae" :
    trend === "NEGATIVE" ? "#ff4d4d" : "#888";

  const sign = diff > 0 ? "+" : "";

  return (
    <div className="performance-container">
      <div className="performance-header">
        <span className="perf-label">{label}</span>
        <span className="perf-values">
          <span className="perf-factory">{factoryValue}{unit}</span>
          <span className="perf-arrow"> → </span>
          <span className="perf-current" style={{ color }}>{currentValue}{unit}</span>
        </span>
      </div>

      <div className="performance-bar">
        <div className="performance-base" style={{ width: `${baseWidth}%` }} />
        <div className="performance-diff" style={{ left: `${start}%`, width: `${diffWidth}%`, background: color }} />
      </div>

      <small className="perf-diff" style={{ color }}>
        {sign}{diff}{unit} gain
      </small>
    </div>
  );
}