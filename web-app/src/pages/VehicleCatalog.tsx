export default function VehicleCatalog() {
  const [vehicles, setVehicles] = useState<any[]>([]);

  useEffect(() => {
    fetchVehicles().then(setVehicles);
  }, []);

  return (
    <div>
      <h1>Vehicle Catalog</h1>

      {vehicles.map((v) => (
        <div key={v.id}>
          {v.brand} {v.model}
        </div>
      ))}
    </div>
  );
}