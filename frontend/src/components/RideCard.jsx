export default function RideCard({ ride, onBook, bookings, onCancelRide }) {
  if (!ride) return null;

  const showPassengers = Array.isArray(bookings);

  return (
    <div className="ride-card">
      <div>
        <strong>{ride.from_location}</strong> → <strong>{ride.to_location}</strong>
      </div>
      <div style={{ fontSize: "0.9rem", marginTop: 6, color: "var(--color-muted)" }}>
        {ride.date} · {ride.time}
      </div>
      <div style={{ fontSize: "0.9rem", marginTop: 4 }}>
        Seats: {ride.available_seats} · Price per seat: {ride.price_per_seat}
      </div>

      {showPassengers && (
        <div className="ride-card__divider">
          <p className="ride-card__passengers-title">Passengers</p>
          {bookings.length === 0 ? (
            <p className="form__hint" style={{ margin: 0 }}>
              No bookings yet.
            </p>
          ) : (
            <ul className="ride-card__passenger-list">
              {bookings.map((b) => (
                <li key={b.booking_id}>
                  <strong>{b.passenger_name || "Passenger"}</strong>
                  {b.passenger_phone && (
                    <>
                      {" "}
                      ·{" "}
                      <a href={`tel:${b.passenger_phone}`} style={{ color: "var(--color-primary)" }}>
                        {b.passenger_phone}
                      </a>
                    </>
                  )}
                  {b.passenger_email && (
                    <span style={{ color: "var(--color-muted)" }}> · {b.passenger_email}</span>
                  )}
                  <span style={{ color: "var(--color-muted)" }}> · {b.status}</span>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}

      {onBook && (
        <div className="ride-card__actions">
          <button type="button" className="btn btn--primary btn--sm" onClick={() => onBook(ride.ride_id)}>
            Book
          </button>
        </div>
      )}

      {typeof onCancelRide === "function" && ride.status === "ACTIVE" && (
        <div className="ride-card__actions">
          <button type="button" className="btn btn--secondary btn--sm" onClick={onCancelRide}>
            Cancel ride
          </button>
        </div>
      )}
    </div>
  );
}
