import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { register } from "../api/authApi";
import "./LoginPage.css";

export default function RegisterPage() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(event: React.FormEvent) {
    event.preventDefault();

    try {
      await register({
        username,
        email,
        password,
      });

      navigate("/login");
    } catch (error) {
      console.error(error);
      setError("Erro ao criar conta.");
    }
  }

  return (
    <main className="login-page">
      <section className="login-card">
        <h1 className="login-title">Criar conta</h1>

        <p className="login-subtitle">
          Cadastre-se para acessar sua garagem digital
        </p>

        <form className="login-form" onSubmit={handleSubmit}>
          <input
            className="login-input"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />

          <input
            className="login-input"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            className="login-input"
            type="password"
            placeholder="Senha"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="login-button" type="submit">
            Criar conta
          </button>

          {error && <p className="login-error">{error}</p>}
        </form>

        <p className="login-register-link">
          Já tem conta? <Link to="/login">Entrar</Link>
        </p>
      </section>
    </main>
  );
}