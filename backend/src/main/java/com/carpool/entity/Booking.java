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
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "booking_id")
  private Long bookingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ride_id", nullable = false)
  private Ride ride;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "passenger_id", nullable = false)
  private Passenger passenger;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private BookingStatus status = BookingStatus.BOOKED;

  @Column(name = "recorded_from", length = 255)
  private String recordedFromLocation;

  @Column(name = "recorded_to", length = 255)
  private String recordedToLocation;

  @Column(name = "recorded_ride_date")
  private LocalDate recordedRideDate;

  @Column(name = "recorded_ride_time")
  private LocalTime recordedRideTime;

  @Column(name = "recorded_price_per_seat", precision = 10, scale = 2)
  private BigDecimal recordedPricePerSeat;

  @Column(name = "recorded_passenger_name", length = 255)
  private String recordedPassengerName;

  @Column(name = "recorded_driver_name", length = 255)
  private String recordedDriverName;

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public Ride getRide() {
    return ride;
  }

  public void setRide(Ride ride) {
    this.ride = ride;
  }

  public Passenger getPassenger() {
    return passenger;
  }

  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
  }

  public BookingStatus getStatus() {
    return status;
  }

  public void setStatus(BookingStatus status) {
    this.status = status;
  }

  public String getRecordedFromLocation() {
    return recordedFromLocation;
  }

  public void setRecordedFromLocation(String recordedFromLocation) {
    this.recordedFromLocation = recordedFromLocation;
  }

  public String getRecordedToLocation() {
    return recordedToLocation;
  }

  public void setRecordedToLocation(String recordedToLocation) {
    this.recordedToLocation = recordedToLocation;
  }

  public LocalDate getRecordedRideDate() {
    return recordedRideDate;
  }

  public void setRecordedRideDate(LocalDate recordedRideDate) {
    this.recordedRideDate = recordedRideDate;
  }

  public LocalTime getRecordedRideTime() {
    return recordedRideTime;
  }

  public void setRecordedRideTime(LocalTime recordedRideTime) {
    this.recordedRideTime = recordedRideTime;
  }

  public BigDecimal getRecordedPricePerSeat() {
    return recordedPricePerSeat;
  }

  public void setRecordedPricePerSeat(BigDecimal recordedPricePerSeat) {
    this.recordedPricePerSeat = recordedPricePerSeat;
  }

  public String getRecordedPassengerName() {
    return recordedPassengerName;
  }

  public void setRecordedPassengerName(String recordedPassengerName) {
    this.recordedPassengerName = recordedPassengerName;
  }

  public String getRecordedDriverName() {
    return recordedDriverName;
  }

  public void setRecordedDriverName(String recordedDriverName) {
    this.recordedDriverName = recordedDriverName;
  }
}
