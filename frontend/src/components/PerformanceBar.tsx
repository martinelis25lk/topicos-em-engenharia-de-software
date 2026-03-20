interface Props {
    label: string;
    factoryValue: number;
    currentValue: number; // camelCase
}

export function PerformanceBar({
    label,
    factoryValue,
    currentValue
}: Props) {
    const gain = currentValue - factoryValue;
    const percent = ((currentValue - factoryValue) / factoryValue) * 100;

    return (
        <div>
            <p>{label}</p>
            <div className="bar">
                <div
                    style={{ width: `${Math.abs(percent)}%` }}
                    className={gain >= 0 ? "green" : "red"}
                />
            </div>
        </div>
    );
}