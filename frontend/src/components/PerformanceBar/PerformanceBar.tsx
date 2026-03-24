import "./PerformanceBar.css";

interface Props {
  label: string;
  factoryValue: number;
  currentValue: number;
  diff: number;
  trend: "POSITIVE" | "NEGATIVE" | "NEUTRAL";
}

export function PerformanceBar({
  label,
  factoryValue,
  currentValue,
  diff,
  trend
}: Props) {
  const max = Math.max(factoryValue, currentValue);
  const baseWidth = (factoryValue / max) * 100;
  const currentWidth = (currentValue / max) * 100;
  const diffWidth = Math.abs(currentWidth - baseWidth);

  const start = Math.min(baseWidth, currentWidth);

  const color =
    trend === "POSITIVE"
      ? "#00ffae"
      : trend === "NEGATIVE"
      ? "#ff4d4d"
      : "#888";

  return (
    <div className="performance-container">
      <div className="performance-header">
        <span>{label}</span>
        <span>{factoryValue} → {currentValue}</span>
      </div>

      <div className="performance-bar">
        <div
          className="performance-base"
          style={{ width: `${baseWidth}%` }}
        />

        <div
          className="performance-diff"
          style={{
            left: `${start}%`,
            width: `${diffWidth}%`,
            background: color
          }}
        />
      </div>

      <small style={{ color }}>
        {diff > 0 ? "+" : ""}
        {diff}
      </small>
    </div>
  );
}