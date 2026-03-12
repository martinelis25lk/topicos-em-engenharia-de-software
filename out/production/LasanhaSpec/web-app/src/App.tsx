import { useEffect } from "react";

function App() {

  useEffect(() => {
    fetch("http://localhost:8080/vehicles")
      .then(res => res.json())
      .then(data => console.log(data));
  }, []);

  return (
    <h1>LasanhaSpec Frontend</h1>
  );
}

export default App;