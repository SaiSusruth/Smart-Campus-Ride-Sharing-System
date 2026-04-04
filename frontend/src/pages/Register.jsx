import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { api } from "../services/api";

export default function Register() {
  const [role, setRole] = useState("PASSENGER");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [vehicleNumber, setVehicleNumber] = useState("");
  const [registrationNumber, setRegistrationNumber] = useState("");
  const [drivingLicenseNumber, setDrivingLicenseNumber] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      const payload = {
        name,
        email,
        phone,
        password,
        role,
      };
      if (role === "DRIVER") {
        payload.vehicleNumber = vehicleNumber.trim();
        payload.registrationNumber = registrationNumber.trim();
        payload.drivingLicenseNumber = drivingLicenseNumber.trim();
      }
      await api.register(payload);
      if (role === "DRIVER") {
        navigate("/driver/pending-approval");
      } else {
        navigate("/login", {
          state: { message: "Registration successful. Please login to continue." },
        });
      }
    } catch (err) {
      setError(err.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  const isDriver = role === "DRIVER";

  return (
    <div className="page">
      <h1 className="page__title">Create account</h1>
      <p className="page__subtitle">Join as a passenger or register as a driver with your vehicle details.</p>

      <div className="card">
        <form className="form" onSubmit={onSubmit}>
          <div className="form__group">
            <label htmlFor="reg-role">Role</label>
            <select id="reg-role" value={role} onChange={(e) => setRole(e.target.value)} required>
              <option value="PASSENGER">Passenger</option>
              <option value="DRIVER">Driver</option>
            </select>
          </div>

          <div className="form__group">
            <label htmlFor="reg-name">Full name</label>
            <input
              id="reg-name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              autoComplete="name"
            />
          </div>
          <div className="form__group">
            <label htmlFor="reg-email">Email</label>
            <input
              id="reg-email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              type="email"
              required
              autoComplete="email"
            />
          </div>
          <div className="form__group">
            <label htmlFor="reg-phone">Phone</label>
            <input
              id="reg-phone"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              required
              autoComplete="tel"
            />
          </div>
          <div className="form__group">
            <label htmlFor="reg-password">Password</label>
            <input
              id="reg-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              type="password"
              required
              autoComplete="new-password"
            />
          </div>

          {isDriver && (
            <div className="form__section">
              <p className="form__section-title">Driver &amp; vehicle</p>
              <div className="form__group">
                <label htmlFor="reg-vehicle">Vehicle number (plate)</label>
                <input
                  id="reg-vehicle"
                  value={vehicleNumber}
                  onChange={(e) => setVehicleNumber(e.target.value)}
                  required={isDriver}
                  autoComplete="off"
                />
              </div>
              <div className="form__group">
                <label htmlFor="reg-rc">Registration certificate (RC) number</label>
                <input
                  id="reg-rc"
                  value={registrationNumber}
                  onChange={(e) => setRegistrationNumber(e.target.value)}
                  required={isDriver}
                  autoComplete="off"
                />
              </div>
              <div className="form__group">
                <label htmlFor="reg-license">Driving license number</label>
                <input
                  id="reg-license"
                  value={drivingLicenseNumber}
                  onChange={(e) => setDrivingLicenseNumber(e.target.value)}
                  required={isDriver}
                  autoComplete="off"
                />
              </div>
            </div>
          )}

          {error && <div className="alert alert--error">{error}</div>}
          <button className="btn btn--primary" type="submit" disabled={loading}>
            {loading ? "Creating account…" : "Create account"}
          </button>
        </form>
      </div>

      <p className="link-row">
        Already registered? <Link to="/login">Sign in</Link>
      </p>
    </div>
  );
}
