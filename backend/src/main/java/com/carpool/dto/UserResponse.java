package com.carpool.dto;

import com.carpool.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
  private Long id;
  private String name;
  private String email;
  private Role role;
  private String phone;

  @JsonProperty("is_verified")
  private boolean verified;

  @JsonProperty("vehicle_number")
  private String vehicleNumber;

  @JsonProperty("registration_number")
  private String registrationNumber;

  @JsonProperty("driving_license_number")
  private String drivingLicenseNumber;

  public UserResponse() {}

  public UserResponse(
      Long id,
      String name,
      String email,
      Role role,
      String phone,
      boolean verified,
      String vehicleNumber,
      String registrationNumber,
      String drivingLicenseNumber) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
    this.phone = phone;
    this.verified = verified;
    this.vehicleNumber = vehicleNumber;
    this.registrationNumber = registrationNumber;
    this.drivingLicenseNumber = drivingLicenseNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public String getVehicleNumber() {
    return vehicleNumber;
  }

  public void setVehicleNumber(String vehicleNumber) {
    this.vehicleNumber = vehicleNumber;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getDrivingLicenseNumber() {
    return drivingLicenseNumber;
  }

  public void setDrivingLicenseNumber(String drivingLicenseNumber) {
    this.drivingLicenseNumber = drivingLicenseNumber;
  }
}
