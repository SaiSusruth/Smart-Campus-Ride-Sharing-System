package com.carpool.dto;

import com.carpool.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
  @NotBlank private String name;

  @NotBlank @Email private String email;

  @NotBlank private String phone;

  @NotBlank private String password;

  @NotNull private Role role;

  /** Required when role is DRIVER — vehicle plate / number. */
  private String vehicleNumber;

  /** Required when role is DRIVER — RC / registration certificate number. */
  private String registrationNumber;

  /** Required when role is DRIVER — driving license number. */
  private String drivingLicenseNumber;

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
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
