export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

async function apiRequest(path, { method = "GET", body, headers = {} } = {}) {
  const url = `${API_BASE_URL}${path}`;

  const options = {
    method,
    credentials: "include",
    headers: { ...headers },
  };

  if (body !== undefined) {
    options.headers["Content-Type"] = "application/json";
    options.body = JSON.stringify(body);
  }

  const res = await fetch(url, options);

  if (res.status === 204) return null;

  const text = await res.text();
  if (!text) return null;

  let data = null;
  try {
    data = JSON.parse(text);
  } catch {
    data = { message: text };
  }

  if (!res.ok) {
    const message = data?.message || res.statusText || "Request failed";
    throw new Error(message);
  }

  return data;
}

export const api = {
  me: () => apiRequest("/auth/me"),

  logout: () => apiRequest("/auth/logout", { method: "POST" }),

  register: ({
    name,
    email,
    phone,
    password,
    role,
    vehicleNumber,
    registrationNumber,
    drivingLicenseNumber,
  }) =>
    apiRequest("/auth/register", {
      method: "POST",
      body: {
        name,
        email,
        phone,
        password,
        role,
        ...(role === "DRIVER" && {
          vehicleNumber,
          registrationNumber,
          drivingLicenseNumber,
        }),
      },
    }),

  login: ({ email, password }) => apiRequest("/auth/login", { method: "POST", body: { email, password } }),

  pendingDrivers: () => apiRequest("/admin/pending-drivers"),

  verifyDriver: (userId) => apiRequest(`/admin/verify/${userId}`, { method: "PUT" }),

  rejectDriver: (userId) => apiRequest(`/admin/reject/${userId}`, { method: "PUT" }),

  createRide: (payload) => apiRequest("/rides/create", { method: "POST", body: payload }),

  ridesAll: () => apiRequest("/rides/all"),

  myRides: () => apiRequest("/rides/my"),

  searchRides: ({ from, to, date }) =>
    apiRequest(`/rides/search?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}&date=${encodeURIComponent(date)}`),

  cancelRide: (rideId) => apiRequest(`/rides/cancel/${rideId}`, { method: "PUT" }),

  bookRide: (rideId) => apiRequest(`/book/${rideId}`, { method: "POST" }),

  myBookings: () => apiRequest("/my-bookings"),

  cancelBooking: (bookingId) => apiRequest(`/cancel-booking/${bookingId}`, { method: "DELETE" }),
};
