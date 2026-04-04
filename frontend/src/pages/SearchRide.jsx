import { useState } from "react";
import { api } from "../services/api";
import RideCard from "../components/RideCard";

export default function SearchRide() {
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [date, setDate] = useState("");
  const [rides, setRides] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const search = async () => {
    setLoading(true);
    setError(null);
    try {
      const result = await api.searchRides({ from, to, date });
      const list = Array.isArray(result) ? result : result?.rides || [];
      setRides(list);
    } catch (err) {
      setError(err.message || "Search failed");
      setRides([]);
    } finally {
      setLoading(false);
    }
  };

  const onBook = async (rideId) => {
    await api.bookRide(rideId);
    alert("Ride booked successfully");
  };

  return (
    <div className="page page--wide">
      <h1 className="page__title">Search rides</h1>
      <p className="page__subtitle">Enter route and date to find available seats.</p>

      <div className="card" style={{ marginBottom: "1.25rem" }}>
        <div className="form">
          <div className="form__group">
            <label htmlFor="search-from">From</label>
            <input
              id="search-from"
              placeholder="Pickup area"
              value={from}
              onChange={(e) => setFrom(e.target.value)}
            />
          </div>
          <div className="form__group">
            <label htmlFor="search-to">To</label>
            <input id="search-to" placeholder="Drop-off area" value={to} onChange={(e) => setTo(e.target.value)} />
          </div>
          <div className="form__group">
            <label htmlFor="search-date">Date</label>
            <input id="search-date" type="date" value={date} onChange={(e) => setDate(e.target.value)} />
          </div>
          {error && <div className="alert alert--error">{error}</div>}
          <button type="button" className="btn btn--primary" onClick={search} disabled={loading}>
            {loading ? "Searching…" : "Search"}
          </button>
        </div>
      </div>

      {rides.length === 0 ? (
        <p className="page__subtitle" style={{ marginTop: 0 }}>
          No rides to show yet. Try a search.
        </p>
      ) : (
        <div className="ride-grid">
          {rides.map((r) => (
            <RideCard key={r.ride_id} ride={r} onBook={onBook} />
          ))}
        </div>
      )}
    </div>
  );
}
