package com.carpool.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "pending_drivers")
public class PendingDriver {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String phone;

  @Column(name = "vehicle_number", nullable = false)
  private String vehicleNumber;

  @Column(name = "registration_number", nullable = false)
  private String registrationNumber;

  @Column(name = "driving_license_number", nullable = false)
  private String drivingLicenseNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_status", nullable = false, length = 20)
  private PendingAccountStatus accountStatus = PendingAccountStatus.PENDING;

  @Column(length = 2000)
  private String remarks;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "approved_driver_id")
  private Driver approvedDriver;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public PendingAccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(PendingAccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Driver getApprovedDriver() {
    return approvedDriver;
  }

  public void setApprovedDriver(Driver approvedDriver) {
    this.approvedDriver = approvedDriver;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
