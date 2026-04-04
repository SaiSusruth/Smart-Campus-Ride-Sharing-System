/** Minimal header for login / register — no session-based links or logout. */
export default function AuthTopBar() {
  return (
    <header className="auth-top-bar">
      <span className="auth-top-bar__brand">Smart Campus Rides</span>
    </header>
  );
}
