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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "rides")
public class Ride {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ride_id")
  private Long rideId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "driver_id", nullable = false)
  private Driver driver;

  @Column(name = "from_location", nullable = false, length = 255)
  private String fromLocation;

  @Column(name = "to_location", nullable = false, length = 255)
  private String toLocation;

  @Column(name = "ride_date", nullable = false)
  private LocalDate rideDate;

  @Column(name = "ride_time", nullable = false)
  private LocalTime rideTime;

  @Column(name = "available_seats", nullable = false)
  private int availableSeats;

  @Column(name = "price_per_seat", nullable = false, precision = 10, scale = 2)
  private BigDecimal pricePerSeat;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private RideStatus status = RideStatus.ACTIVE;

  public Long getRideId() {
    return rideId;
  }

  public void setRideId(Long rideId) {
    this.rideId = rideId;
  }

  public Driver getDriver() {
    return driver;
  }

  public void setDriver(Driver driver) {
    this.driver = driver;
  }

  public String getFromLocation() {
    return fromLocation;
  }

  public void setFromLocation(String fromLocation) {
    this.fromLocation = fromLocation;
  }

  public String getToLocation() {
    return toLocation;
  }

  public void setToLocation(String toLocation) {
    this.toLocation = toLocation;
  }

  public LocalDate getRideDate() {
    return rideDate;
  }

  public void setRideDate(LocalDate rideDate) {
    this.rideDate = rideDate;
  }

  public LocalTime getRideTime() {
    return rideTime;
  }

  public void setRideTime(LocalTime rideTime) {
    this.rideTime = rideTime;
  }

  public int getAvailableSeats() {
    return availableSeats;
  }

  public void setAvailableSeats(int availableSeats) {
    this.availableSeats = availableSeats;
  }

  public BigDecimal getPricePerSeat() {
    return pricePerSeat;
  }

  public void setPricePerSeat(BigDecimal pricePerSeat) {
    this.pricePerSeat = pricePerSeat;
  }

  public RideStatus getStatus() {
    return status;
  }

  public void setStatus(RideStatus status) {
    this.status = status;
  }
}
