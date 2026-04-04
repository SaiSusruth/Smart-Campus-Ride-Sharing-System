import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { api } from "../services/api";

export default function DriverDashboard() {
  const [me, setMe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    api
      .me()
      .then(setMe)
      .catch((err) => setError(err.message || "Failed to load user"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="page">
        <p className="page__subtitle">Loading driver dashboard…</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page">
        <div className="alert alert--error">{error}</div>
      </div>
    );
  }

  const verified = !!me?.is_verified;

  return (
    <div className="page">
      <h1 className="page__title">Driver</h1>
      <p className="page__subtitle">
        {verified
          ? "You are verified — offer rides and track bookings."
          : "Your registration details are with the admin. You will be able to offer rides after approval."}
      </p>

      {me?.vehicle_number && (
        <div className="card" style={{ marginBottom: "1rem" }}>
          <strong>Registered vehicle</strong>
          <div className="driver-card__meta">
            <span>Plate: {me.vehicle_number}</span>
            <span>RC: {me.registration_number}</span>
            <span>License: {me.driving_license_number}</span>
          </div>
        </div>
      )}

      {!verified ? (
        <div className="card card--stack">
          <p style={{ margin: 0 }}>
            An administrator will review your vehicle number, registration number, and driving license number from
            registration. Check back after approval.
          </p>
        </div>
      ) : (
        <div className="dash-links">
          <Link to="/driver/offer">Offer a ride</Link>
          <Link to="/driver/my-rides">My rides</Link>
        </div>
      )}
    </div>
  );
}
