import { Link } from "react-router-dom";

export default function AdminDashboard() {
  return (
    <div className="page">
      <h1 className="page__title">Admin</h1>
      <p className="page__subtitle">Review driver applications and manage the platform.</p>
      <div className="dash-links">
        <Link to="/admin/verify-drivers">Verify drivers</Link>
      </div>
    </div>
  );
}
