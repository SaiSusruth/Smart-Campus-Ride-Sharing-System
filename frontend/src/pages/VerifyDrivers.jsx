import { useEffect, useState } from "react";
import { api } from "../services/api";

export default function VerifyDrivers() {
  const [drivers, setDrivers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = () => {
    setLoading(true);
    setError(null);
    api
      .pendingDrivers()
      .then((res) => {
        const list = Array.isArray(res) ? res : res?.drivers || res?.pendingDrivers || [];
        setDrivers(list);
      })
      .catch((err) => setError(err.message || "Failed to load pending drivers"))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    load();
  }, []);

  const approve = async (userId) => {
    await api.verifyDriver(userId);
    await load();
  };

  const reject = async (userId) => {
    await api.rejectDriver(userId);
    await load();
  };

  if (loading) {
    return (
      <div className="page page--wide">
        <p className="page__subtitle">Loading pending drivers…</p>
      </div>
    );
  }

  return (
    <div className="page page--wide">
      <h1 className="page__title">Pending drivers</h1>
      <p className="page__subtitle">Review vehicle, registration, and license numbers from registration, then approve or reject.</p>
      {error && <div className="alert alert--error">{error}</div>}
      {drivers.length === 0 ? (
        <div className="card">No pending drivers.</div>
      ) : (
        <div className="ride-grid">
          {drivers.map((d) => (
            <div key={d.user_id} className="driver-card">
              <div>
                <strong>{d.name || d.user_name || "Driver"}</strong>
                <span style={{ color: "var(--color-muted)", marginLeft: 8 }}>ID {d.user_id}</span>
              </div>
              <div style={{ fontSize: "0.9rem", marginTop: 4 }}>{d.email}</div>
              <div style={{ marginTop: 6, fontSize: "0.85rem" }}>Status: {d.status}</div>

              {(d.vehicle_number || d.registration_number || d.driving_license_number) && (
                <div className="driver-card__meta">
                  {d.vehicle_number && <span>Vehicle: {d.vehicle_number}</span>}
                  {d.registration_number && <span>RC: {d.registration_number}</span>}
                  {d.driving_license_number && <span>License: {d.driving_license_number}</span>}
                </div>
              )}

              <div className="driver-card__actions">
                <button type="button" className="btn btn--primary btn--sm" onClick={() => approve(d.user_id)}>
                  Approve
                </button>
                <button type="button" className="btn btn--danger btn--sm" onClick={() => reject(d.user_id)}>
                  Reject
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
