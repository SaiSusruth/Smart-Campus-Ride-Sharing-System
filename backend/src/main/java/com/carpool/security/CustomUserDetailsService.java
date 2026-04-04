package com.carpool.security;

import com.carpool.entity.Driver;
import com.carpool.entity.PendingAccountStatus;
import com.carpool.entity.PendingDriver;
import com.carpool.entity.Role;
import com.carpool.repository.AdminRepository;
import com.carpool.repository.DriverRepository;
import com.carpool.repository.PassengerRepository;
import com.carpool.repository.PendingDriverRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final AdminRepository adminRepository;
  private final PassengerRepository passengerRepository;
  private final DriverRepository driverRepository;
  private final PendingDriverRepository pendingDriverRepository;

  public CustomUserDetailsService(
      AdminRepository adminRepository,
      PassengerRepository passengerRepository,
      DriverRepository driverRepository,
      PendingDriverRepository pendingDriverRepository) {
    this.adminRepository = adminRepository;
    this.passengerRepository = passengerRepository;
    this.driverRepository = driverRepository;
    this.pendingDriverRepository = pendingDriverRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username.trim().toLowerCase();

    Optional<com.carpool.entity.Admin> admin = adminRepository.findByEmail(email);
    if (admin.isPresent()) {
      com.carpool.entity.Admin a = admin.get();
      return new CustomUserDetails(AccountKind.ADMIN, a.getId(), a.getEmail(), a.getPassword(), Role.ADMIN);
    }

    Optional<com.carpool.entity.Passenger> passenger = passengerRepository.findByEmail(email);
    if (passenger.isPresent()) {
      com.carpool.entity.Passenger p = passenger.get();
      return new CustomUserDetails(
          AccountKind.PASSENGER, p.getId(), p.getEmail(), p.getPassword(), Role.PASSENGER);
    }

    Optional<Driver> driver = driverRepository.findByEmail(email);
    if (driver.isPresent()) {
      Driver d = driver.get();
      return new CustomUserDetails(AccountKind.DRIVER, d.getId(), d.getEmail(), d.getPassword(), Role.DRIVER);
    }

    Optional<PendingDriver> pending =
        pendingDriverRepository.findByEmail(email).filter(pd -> pd.getAccountStatus() == PendingAccountStatus.PENDING);
    if (pending.isPresent()) {
      PendingDriver pd = pending.get();
      return new CustomUserDetails(
          AccountKind.PENDING_DRIVER, pd.getId(), pd.getEmail(), pd.getPassword(), Role.DRIVER);
    }

    throw new UsernameNotFoundException("User not found");
  }
}
