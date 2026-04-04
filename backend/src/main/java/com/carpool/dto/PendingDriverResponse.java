package com.carpool.dto;

import com.carpool.entity.PendingAccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PendingDriverResponse {

  @JsonProperty("user_id")
  private Long userId;

  private String name;
  private String email;
  private PendingAccountStatus status;

  private String remarks;

  @JsonProperty("vehicle_number")
  private String vehicleNumber;

  @JsonProperty("registration_number")
  private String registrationNumber;

  @JsonProperty("driving_license_number")
  private String drivingLicenseNumber;

  public PendingDriverResponse() {}

  public PendingDriverResponse(
      Long userId,
      String name,
      String email,
      PendingAccountStatus status,
      String remarks,
      String vehicleNumber,
      String registrationNumber,
      String drivingLicenseNumber) {
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.status = status;
    this.remarks = remarks;
    this.vehicleNumber = vehicleNumber;
    this.registrationNumber = registrationNumber;
    this.drivingLicenseNumber = drivingLicenseNumber;
  }

  public Long getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public PendingAccountStatus getStatus() {
    return status;
  }

  public String getRemarks() {
    return remarks;
  }

  public String getVehicleNumber() {
    return vehicleNumber;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public String getDrivingLicenseNumber() {
    return drivingLicenseNumber;
  }
}
