import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { api } from "../services/api";

export default function PassengerDashboard() {
  const [me, setMe] = useState(null);

  useEffect(() => {
    api.me().then(setMe).catch(() => setMe(null));
  }, []);

  return (
    <div className="page">
      <h1 className="page__title">Passenger</h1>
      <p className="page__subtitle">Search for rides and manage your trips.</p>

      {me && (
        <div className="card" style={{ marginBottom: "1.25rem" }}>
          <strong>Profile</strong>
          <div className="driver-card__meta" style={{ marginTop: 8 }}>
            <span>{me.name}</span>
            <span> · {me.email}</span>
            {me.phone && <span> · {me.phone}</span>}
          </div>
          <p className="form__hint" style={{ marginTop: 10, marginBottom: 0 }}>
            After you book a ride, open <strong>My bookings</strong> to see route, driver contact, and vehicle
            (plate &amp; RC) for that trip.
          </p>
        </div>
      )}

      <div className="dash-links">
        <Link to="/passenger/search">Search rides</Link>
        <Link to="/passenger/bookings">My bookings</Link>
      </div>
    </div>
  );
}
