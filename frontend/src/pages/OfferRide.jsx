import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

export default function OfferRide() {
  const navigate = useNavigate();
  const [fromLocation, setFromLocation] = useState("");
  const [toLocation, setToLocation] = useState("");
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [availableSeats, setAvailableSeats] = useState(1);
  const [pricePerSeat, setPricePerSeat] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await api.createRide({
        from_location: fromLocation,
        to_location: toLocation,
        date,
        time,
        available_seats: Number(availableSeats),
        price_per_seat: Number(pricePerSeat),
      });
      alert("Ride created!");
      navigate("/driver/my-rides");
    } catch (err) {
      setError(err.message || "Failed to create ride");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page">
      <h1 className="page__title">Offer a ride</h1>
      <p className="page__subtitle">Publish a trip for passengers to book.</p>

      <div className="card">
        <form className="form" onSubmit={submit}>
          <div className="form__group">
            <label htmlFor="offer-from">From</label>
            <input
              id="offer-from"
              placeholder="Pickup location"
              value={fromLocation}
              onChange={(e) => setFromLocation(e.target.value)}
              required
            />
          </div>
          <div className="form__group">
            <label htmlFor="offer-to">To</label>
            <input
              id="offer-to"
              placeholder="Destination"
              value={toLocation}
              onChange={(e) => setToLocation(e.target.value)}
              required
            />
          </div>
          <div className="form__group">
            <label htmlFor="offer-date">Date</label>
            <input id="offer-date" type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
          </div>
          <div className="form__group">
            <label htmlFor="offer-time">Time</label>
            <input id="offer-time" type="time" value={time} onChange={(e) => setTime(e.target.value)} required />
          </div>
          <div className="form__group">
            <label htmlFor="offer-seats">Available seats</label>
            <input
              id="offer-seats"
              type="number"
              min={1}
              value={availableSeats}
              onChange={(e) => setAvailableSeats(e.target.value)}
              required
            />
          </div>
          <div className="form__group">
            <label htmlFor="offer-price">Price per seat</label>
            <input
              id="offer-price"
              type="number"
              min={0}
              step="0.01"
              value={pricePerSeat}
              onChange={(e) => setPricePerSeat(e.target.value)}
              required
            />
          </div>
          {error && <div className="alert alert--error">{error}</div>}
          <button className="btn btn--primary" type="submit" disabled={loading}>
            {loading ? "Creating…" : "Create ride"}
          </button>
        </form>
      </div>
    </div>
  );
}
