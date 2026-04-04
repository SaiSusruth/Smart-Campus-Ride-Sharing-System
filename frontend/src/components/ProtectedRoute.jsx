import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { api } from "../services/api";

export default function ProtectedRoute({ children, roles = [] }) {
  const [status, setStatus] = useState({ loading: true, user: null, error: null });

  useEffect(() => {
    let mounted = true;

    api
      .me()
      .then((user) => {
        if (!mounted) return;
        setStatus({ loading: false, user, error: null });
      })
      .catch((err) => {
        if (!mounted) return;
        setStatus({ loading: false, user: null, error: err.message || "Auth failed" });
      });

    return () => {
      mounted = false;
    };
  }, []);

  if (status.loading) {
    return (
      <div className="page">
        <p className="page__subtitle">Loading…</p>
      </div>
    );
  }

  if (!status.user) {
    return <Navigate to="/login" replace />;
  }

  if (roles.length > 0 && !roles.includes(status.user.role)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
}

