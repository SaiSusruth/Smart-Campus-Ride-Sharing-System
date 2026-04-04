import { useEffect, useState } from "react";
import { api } from "../services/api";
import RideCard from "../components/RideCard";

export default function DriverMyRides() {
  const [rides, setRides] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = () => {
    setLoading(true);
    setError(null);
    api
      .myRides()
      .then((res) => {
        const list = Array.isArray(res) ? res : res?.rides || [];
        setRides(list);
      })
      .catch((err) => setError(err.message || "Failed to load rides"))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    load();
  }, []);

  const cancelRide = async (rideId) => {
    if (!window.confirm("Cancel this ride?")) return;
    await api.cancelRide(rideId);
    await load();
  };

  if (loading) {
    return (
      <div className="page">
        <p className="page__subtitle">Loading your rides…</p>
      </div>
    );
  }

  return (
    <div className="page page--wide">
      <h1 className="page__title">My rides</h1>
      <p className="page__subtitle">Your published trips and passengers who booked a seat.</p>
      {error && <div className="alert alert--error">{error}</div>}
      {rides.length === 0 ? (
        <div className="card">No rides yet.</div>
      ) : (
        rides.map((r) => (
          <div key={r.ride_id} style={{ marginBottom: "1rem" }}>
            <RideCard
              ride={r}
              bookings={r.bookings || []}
              onCancelRide={() => cancelRide(r.ride_id)}
            />
          </div>
        ))
      )}
    </div>
  );
}
