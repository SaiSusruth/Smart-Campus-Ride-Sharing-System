import { Link, useLocation, useNavigate } from "react-router-dom";
import { api } from "../services/api";
import { useEffect, useState } from "react";

export default function Navbar() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    let cancelled = false;
    api
      .me()
      .then((u) => !cancelled && setUser(u))
      .catch(() => !cancelled && setUser(null));
    return () => {
      cancelled = true;
    };
  }, [location.pathname]);

  const onLogout = async () => {
    try {
      await api.logout();
    } catch {
      // ignore
    } finally {
      setUser(null);
      window.location.href = "/login";
    }
  };

  return (
    <nav className="nav-bar">
      <div className="nav-bar__links">
        <span className="nav-bar__brand">Smart Campus Rides</span>
        {user?.role === "PASSENGER" && (
          <>
            <Link to="/passenger">Dashboard</Link>
            <Link to="/passenger/search">Search rides</Link>
            <Link to="/passenger/bookings">My bookings</Link>
          </>
        )}
        {user?.role === "DRIVER" && (
          <>
            <Link to="/driver">Home</Link>
            <Link to="/driver/my-rides">My rides</Link>
            <Link to="/driver/offer">Offer ride</Link>
          </>
        )}
        {user?.role === "ADMIN" && <Link to="/admin">Admin</Link>}
      </div>
      <div className="nav-bar__auth">
        {user ? (
          <button type="button" className="btn btn--secondary" onClick={onLogout}>
            Log out
          </button>
        ) : (
          <button type="button" className="btn btn--primary" onClick={() => navigate("/login")}>
            Sign in
          </button>
        )}
      </div>
    </nav>
  );
}
