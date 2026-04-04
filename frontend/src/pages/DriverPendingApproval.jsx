import { Link } from "react-router-dom";

export default function DriverPendingApproval() {
  return (
    <div className="page">
      <h1 className="page__title">Application received</h1>
      <p className="page__subtitle">
        Your driver profile is under review. An administrator will approve your account using the vehicle, registration,
        and license details you provided at registration.
      </p>
      <div className="card card--stack">
        <p style={{ margin: 0 }}>Sign in anytime to see your status on the driver dashboard.</p>
        <p style={{ margin: 0 }}>
          <Link to="/login">Go to sign in</Link>
        </p>
      </div>
    </div>
  );
}
