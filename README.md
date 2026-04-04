# Smart Campus Ride Sharing System

Full-stack campus ride-sharing: passengers book seats, drivers offer rides after admin approval, admins verify drivers.

## Stack
- **Backend:** Spring Boot 3, Spring Security (session cookies + BCrypt), Spring Data JPA, MySQL.
- **Frontend:** React (CRA), React Router, `fetch` with `credentials: 'include'`.

## Domain model
- **admins** — Default admin from config (`AdminBootstrapConfig`).
- **passengers** — Register → search/book rides.
- **pending_drivers** — Driver registration + vehicle / registration / license numbers + `PENDING`/`APPROVED`/`REJECTED`.
- **drivers** — Approved drivers only; created on admin verify.
- **rides** — Owned by `Driver`; seats, price, status.
- **bookings** — `Passenger` + `Ride`; snapshots at book time (route, date/time, fare, names).

## Auth
- **No JWT** — HTTP session (`HttpSessionSecurityContextRepository`).
- **Login resolution:** `CustomUserDetailsService` loads by email: admin → passenger → driver → pending (`PENDING` only).
- **`AccountKind`:** Distinguishes approved `DRIVER` vs `PENDING_DRIVER` (pending cannot offer rides until verified).

## Main API areas
| Area | Examples |
|------|----------|
| Auth | `POST /auth/register`, `/login`, `GET /me`, `POST /logout` |
| Rides | `POST /rides/create`, `GET /search`, `/my`, `PUT /cancel/{id}` |
| Bookings | `POST /book/{rideId}`, `GET /my-bookings`, `DELETE /cancel-booking/{id}` |
| Admin | `GET /admin/pending-drivers`, `PUT /verify/{id}`, `PUT /reject/{id}` |

## Frontend
- `src/services/api.js` — API client.
- `App.js` — Routes; `AuthTopBar` on login/register; `Navbar` when authenticated; `ProtectedRoute` + `/auth/me`.

## Config notes
- `app.cors.allowed-origin` — React dev server (e.g. `http://localhost:3000`).
- `spring.jpa.open-in-view=false` — Tighter JPA session boundaries.
- `application-dev.properties` — H2 when `spring.profiles.active=dev`.

## Improvements (talk track)
- CSRF strategy for cookie-based SPA in production.
- Flyway/Liquibase for schema migrations.
- Integration tests; rate limiting on auth.