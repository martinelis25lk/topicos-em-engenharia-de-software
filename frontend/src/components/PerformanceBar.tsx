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

  const baseWidth = 50;

  const variationWidth = Math.min(
    (Math.abs(diff) / (factoryValue || 1)) * 50,
    50
  );

  const color =
    trend === "POSITIVE"
      ? "#00ffae"
      : trend === "NEGATIVE"
      ? "#ff4d4d"
      : "#888";

  return (
    <div style={{ marginBottom: "12px" }}>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <span>{label}</span>
        <span>
          {factoryValue} → {currentValue}
        </span>
      </div>

      <div style={{ display: "flex", height: "8px", borderRadius: "4px", overflow: "hidden" }}>
        <div style={{ width: `${baseWidth}%`, background: "#444" }} />

        <div
          style={{
            width: `${variationWidth}%`,
            background: color,
            transition: "0.3s"
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