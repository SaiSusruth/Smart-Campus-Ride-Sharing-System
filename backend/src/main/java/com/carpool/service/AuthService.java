package com.carpool.service;

import com.carpool.dto.RegisterRequest;
import com.carpool.dto.UserResponse;
import com.carpool.entity.Admin;
import com.carpool.entity.Driver;
import com.carpool.entity.Passenger;
import com.carpool.entity.PendingAccountStatus;
import com.carpool.entity.PendingDriver;
import com.carpool.entity.Role;
import com.carpool.repository.AdminRepository;
import com.carpool.repository.DriverRepository;
import com.carpool.repository.PassengerRepository;
import com.carpool.repository.PendingDriverRepository;
import com.carpool.security.AccountKind;
import com.carpool.security.CustomUserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final AdminRepository adminRepository;
  private final PassengerRepository passengerRepository;
  private final DriverRepository driverRepository;
  private final PendingDriverRepository pendingDriverRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(
      AdminRepository adminRepository,
      PassengerRepository passengerRepository,
      DriverRepository driverRepository,
      PendingDriverRepository pendingDriverRepository,
      PasswordEncoder passwordEncoder) {
    this.adminRepository = adminRepository;
    this.passengerRepository = passengerRepository;
    this.driverRepository = driverRepository;
    this.pendingDriverRepository = pendingDriverRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public UserResponse register(RegisterRequest req) {
    if (req.getRole() == Role.ADMIN) {
      throw new IllegalArgumentException("Admin account cannot be self-registered");
    }

    String email = req.getEmail().trim().toLowerCase();
    if (emailTaken(email)) {
      throw new IllegalArgumentException("Email already registered");
    }

    if (req.getRole() == Role.DRIVER) {
      requireNonBlank(req.getVehicleNumber(), "Vehicle number is required for drivers");
      requireNonBlank(req.getRegistrationNumber(), "Registration number is required for drivers");
      requireNonBlank(req.getDrivingLicenseNumber(), "Driving license number is required for drivers");
    }

    return switch (req.getRole()) {
      case PASSENGER -> toResponse(savePassenger(req, email));
      case DRIVER -> toResponse(savePendingDriver(req, email));
      case ADMIN -> throw new IllegalArgumentException("Admin account cannot be self-registered");
    };
  }

  private boolean emailTaken(String email) {
    if (adminRepository.existsByEmail(email)) {
      return true;
    }
    if (passengerRepository.existsByEmail(email)) {
      return true;
    }
    if (driverRepository.existsByEmail(email)) {
      return true;
    }
    return pendingDriverRepository.existsByEmailAndAccountStatus(email, PendingAccountStatus.PENDING);
  }

  private Passenger savePassenger(RegisterRequest req, String email) {
    Passenger p = new Passenger();
    p.setName(req.getName());
    p.setEmail(email);
    p.setPhone(req.getPhone());
    p.setPassword(passwordEncoder.encode(req.getPassword()));
    return passengerRepository.save(p);
  }

  private PendingDriver savePendingDriver(RegisterRequest req, String email) {
    pendingDriverRepository
        .findByEmail(email)
        .filter(pd -> pd.getAccountStatus() == PendingAccountStatus.REJECTED)
        .ifPresent(pendingDriverRepository::delete);

    PendingDriver pd = new PendingDriver();
    pd.setName(req.getName());
    pd.setEmail(email);
    pd.setPhone(req.getPhone());
    pd.setPassword(passwordEncoder.encode(req.getPassword()));
    pd.setVehicleNumber(req.getVehicleNumber().trim());
    pd.setRegistrationNumber(req.getRegistrationNumber().trim());
    pd.setDrivingLicenseNumber(req.getDrivingLicenseNumber().trim());
    pd.setAccountStatus(PendingAccountStatus.PENDING);
    return pendingDriverRepository.save(pd);
  }

  private static void requireNonBlank(String value, String message) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(message);
    }
  }

  public UserResponse toResponse(CustomUserDetails principal) {
    return switch (principal.getAccountKind()) {
      case ADMIN -> toResponse(adminRepository.findById(principal.getId()).orElseThrow());
      case PASSENGER -> toResponse(passengerRepository.findById(principal.getId()).orElseThrow());
      case DRIVER -> toResponse(driverRepository.findById(principal.getId()).orElseThrow());
      case PENDING_DRIVER -> toResponse(pendingDriverRepository.findById(principal.getId()).orElseThrow());
    };
  }

  public UserResponse toResponse(Admin a) {
    return new UserResponse(
        a.getId(),
        a.getName(),
        a.getEmail(),
        Role.ADMIN,
        a.getPhone(),
        true,
        null,
        null,
        null);
  }

  public UserResponse toResponse(Passenger p) {
    return new UserResponse(
        p.getId(),
        p.getName(),
        p.getEmail(),
        Role.PASSENGER,
        p.getPhone(),
        true,
        null,
        null,
        null);
  }

  public UserResponse toResponse(Driver d) {
    return new UserResponse(
        d.getId(),
        d.getName(),
        d.getEmail(),
        Role.DRIVER,
        d.getPhone(),
        true,
        d.getVehicleNumber(),
        d.getRegistrationNumber(),
        d.getDrivingLicenseNumber());
  }

  public UserResponse toResponse(PendingDriver pd) {
    return new UserResponse(
        pd.getId(),
        pd.getName(),
        pd.getEmail(),
        Role.DRIVER,
        pd.getPhone(),
        false,
        pd.getVehicleNumber(),
        pd.getRegistrationNumber(),
        pd.getDrivingLicenseNumber());
  }
}
