import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { api } from "../services/api";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const onSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      await api.login({ email, password });
      const me = await api.me();
      if (me.role === "PASSENGER") navigate("/passenger");
      else if (me.role === "DRIVER") navigate("/driver");
      else navigate("/admin");
    } catch (err) {
      setError(err.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page">
      <h1 className="page__title">Sign in</h1>
      <p className="page__subtitle">
        Admin can use the default credentials from your server config. Passengers and drivers should register first.
      </p>
      {location.state?.message && (
        <div className="alert alert--success" style={{ marginBottom: "1rem" }}>
          {location.state.message}
        </div>
      )}

      <div className="card">
        <form className="form" onSubmit={onSubmit}>
          <div className="form__group">
            <label htmlFor="login-email">Email</label>
            <input
              id="login-email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              type="email"
              required
              autoComplete="email"
            />
          </div>
          <div className="form__group">
            <label htmlFor="login-password">Password</label>
            <input
              id="login-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              type="password"
              required
              autoComplete="current-password"
            />
          </div>
          {error && <div className="alert alert--error">{error}</div>}
          <button className="btn btn--primary" type="submit" disabled={loading}>
            {loading ? "Signing in…" : "Sign in"}
          </button>
        </form>
      </div>

      <p className="link-row">
        No account? <Link to="/register">Register</Link>
      </p>
    </div>
  );
}
