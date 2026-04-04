export default function Unauthorized() {
  return (
    <div className="page">
      <h1 className="page__title">Unauthorized</h1>
      <p className="page__subtitle">You do not have access to this page.</p>
      <div className="alert alert--error">Sign in with an account that has permission to view this route.</div>
    </div>
  );
}
