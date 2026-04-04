import "./App.css";
import { BrowserRouter, Routes, Route, Navigate, useLocation } from "react-router-dom";

import Navbar from "./components/Navbar";
import AuthTopBar from "./components/AuthTopBar";
import ProtectedRoute from "./components/ProtectedRoute";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Unauthorized from "./pages/Unauthorized";
import DriverPendingApproval from "./pages/DriverPendingApproval";

import PassengerDashboard from "./pages/PassengerDashboard";
import SearchRide from "./pages/SearchRide";
import MyBookings from "./pages/MyBookings";

import DriverDashboard from "./pages/DriverDashboard";
import OfferRide from "./pages/OfferRide";
import DriverMyRides from "./pages/DriverMyRides";

import AdminDashboard from "./pages/AdminDashboard";
import VerifyDrivers from "./pages/VerifyDrivers";

function AppShell() {
  const { pathname } = useLocation();
  const authPages = pathname === "/login" || pathname === "/register";

  return (
    <>
      {authPages ? <AuthTopBar /> : <Navbar />}
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/driver/pending-approval" element={<DriverPendingApproval />} />
        <Route path="/unauthorized" element={<Unauthorized />} />

        <Route
          path="/passenger"
          element={
            <ProtectedRoute roles={["PASSENGER"]}>
              <PassengerDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/passenger/search"
          element={
            <ProtectedRoute roles={["PASSENGER"]}>
              <SearchRide />
            </ProtectedRoute>
          }
        />
        <Route
          path="/passenger/bookings"
          element={
            <ProtectedRoute roles={["PASSENGER"]}>
              <MyBookings />
            </ProtectedRoute>
          }
        />

        <Route
          path="/driver"
          element={
            <ProtectedRoute roles={["DRIVER"]}>
              <DriverDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/driver/offer"
          element={
            <ProtectedRoute roles={["DRIVER"]}>
              <OfferRide />
            </ProtectedRoute>
          }
        />
        <Route
          path="/driver/my-rides"
          element={
            <ProtectedRoute roles={["DRIVER"]}>
              <DriverMyRides />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin"
          element={
            <ProtectedRoute roles={["ADMIN"]}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/verify-drivers"
          element={
            <ProtectedRoute roles={["ADMIN"]}>
              <VerifyDrivers />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AppShell />
    </BrowserRouter>
  );
}
