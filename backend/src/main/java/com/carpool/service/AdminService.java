package com.carpool.service;

import com.carpool.dto.PendingDriverResponse;
import com.carpool.entity.Driver;
import com.carpool.entity.PendingAccountStatus;
import com.carpool.entity.PendingDriver;
import com.carpool.repository.DriverRepository;
import com.carpool.repository.PendingDriverRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

  private final PendingDriverRepository pendingDriverRepository;
  private final DriverRepository driverRepository;

  public AdminService(
      PendingDriverRepository pendingDriverRepository, DriverRepository driverRepository) {
    this.pendingDriverRepository = pendingDriverRepository;
    this.driverRepository = driverRepository;
  }

  public List<PendingDriverResponse> pendingDrivers() {
    return pendingDriverRepository.findByAccountStatusOrderByIdAsc(PendingAccountStatus.PENDING).stream()
        .map(this::toPending)
        .toList();
  }

  private PendingDriverResponse toPending(PendingDriver pd) {
    return new PendingDriverResponse(
        pd.getId(),
        pd.getName(),
        pd.getEmail(),
        pd.getAccountStatus(),
        pd.getRemarks(),
        pd.getVehicleNumber(),
        pd.getRegistrationNumber(),
        pd.getDrivingLicenseNumber());
  }

  @Transactional
  public void verify(Long pendingDriverId) {
    PendingDriver pd =
        pendingDriverRepository
            .findById(pendingDriverId)
            .orElseThrow(() -> new IllegalArgumentException("Pending driver not found"));
    if (pd.getAccountStatus() != PendingAccountStatus.PENDING) {
      throw new IllegalStateException("Driver application is not pending");
    }

    Driver d = new Driver();
    d.setName(pd.getName());
    d.setEmail(pd.getEmail());
    d.setPassword(pd.getPassword());
    d.setPhone(pd.getPhone());
    d.setVehicleNumber(pd.getVehicleNumber());
    d.setRegistrationNumber(pd.getRegistrationNumber());
    d.setDrivingLicenseNumber(pd.getDrivingLicenseNumber());
    driverRepository.save(d);

    pd.setAccountStatus(PendingAccountStatus.APPROVED);
    pd.setApprovedDriver(d);
    pd.setRemarks(null);
    pendingDriverRepository.save(pd);
  }

  @Transactional
  public void reject(Long pendingDriverId, String remarks) {
    PendingDriver pd =
        pendingDriverRepository
            .findById(pendingDriverId)
            .orElseThrow(() -> new IllegalArgumentException("Pending driver not found"));
    if (pd.getAccountStatus() != PendingAccountStatus.PENDING) {
      throw new IllegalStateException("Driver application is not pending");
    }
    pd.setAccountStatus(PendingAccountStatus.REJECTED);
    pd.setRemarks(remarks);
    pendingDriverRepository.save(pd);
  }
}
