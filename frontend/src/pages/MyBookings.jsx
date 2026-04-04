import { useEffect, useState } from "react";
import { api } from "../services/api";

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = () =>
    api.myBookings().then((res) => {
      const list = Array.isArray(res) ? res : res?.bookings || [];
      setBookings(list);
    });

  useEffect(() => {
    let mounted = true;
    load()
      .catch((err) => mounted && setError(err.message || "Failed to load bookings"))
      .finally(() => mounted && setLoading(false));
    return () => {
      mounted = false;
    };
  }, []);

  if (loading) {
    return (
      <div className="page">
        <p className="page__subtitle">Loading bookings…</p>
      </div>
    );
  }

  return (
    <div className="page page--wide">
      <h1 className="page__title">My bookings</h1>
      <p className="page__subtitle">
        Your trips: route, date, time, fare, and driver contact. Details reflect what was saved when you booked.
      </p>
      {error && <div className="alert alert--error">{error}</div>}
      {bookings.length === 0 ? (
        <div className="card">No bookings yet.</div>
      ) : (
        <div className="bookings-table-wrap">
          <table className="bookings-table">
            <thead>
              <tr>
                <th>Route</th>
                <th>Date</th>
                <th>Time</th>
                <th>Amount (₹ / seat)</th>
                <th>Driver</th>
                <th>Status</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bookings.map((b) => (
                <tr key={b.booking_id}>
                  <td>
                    <strong>{b.from_location || "—"}</strong>
                    <br />
                    <span style={{ color: "var(--color-muted)" }}>→ {b.to_location || "—"}</span>
                  </td>
                  <td>{b.date || "—"}</td>
                  <td>{b.time || "—"}</td>
                  <td>{b.price_per_seat != null ? Number(b.price_per_seat).toFixed(2) : "—"}</td>
                  <td>
                    {b.driver_name || "—"}
                    {b.driver_phone && (
                      <>
                        <br />
                        <a href={`tel:${b.driver_phone}`} style={{ color: "var(--color-primary)", fontSize: "0.85rem" }}>
                          {b.driver_phone}
                        </a>
                      </>
                    )}
                  </td>
                  <td>
                    {b.status}
                    {b.ride_status && b.ride_status !== "ACTIVE" && (
                      <div style={{ fontSize: "0.8rem", color: "var(--color-muted)" }}>Ride: {b.ride_status}</div>
                    )}
                  </td>
                  <td>
                    {b.status === "BOOKED" && (
                      <button
                        type="button"
                        className="btn btn--secondary btn--sm"
                        onClick={async () => {
                          if (!window.confirm("Cancel this booking?")) return;
                          try {
                            await api.cancelBooking(b.booking_id);
                            await load();
                          } catch (err) {
                            alert(err.message || "Cancel failed");
                          }
                        }}
                      >
                        Cancel
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
