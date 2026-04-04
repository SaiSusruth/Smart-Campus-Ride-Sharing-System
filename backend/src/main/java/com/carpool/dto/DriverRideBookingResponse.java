package com.carpool.dto;

import com.carpool.entity.Booking;
import com.carpool.entity.BookingStatus;
import com.carpool.entity.Passenger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverRideBookingResponse {

  @JsonProperty("booking_id")
  private Long bookingId;

  private BookingStatus status;

  @JsonProperty("passenger_id")
  private Long passengerId;

  @JsonProperty("passenger_name")
  private String passengerName;

  @JsonProperty("passenger_email")
  private String passengerEmail;

  @JsonProperty("passenger_phone")
  private String passengerPhone;

  public static DriverRideBookingResponse from(Booking b) {
    Passenger p = b.getPassenger();
    DriverRideBookingResponse r = new DriverRideBookingResponse();
    r.bookingId = b.getBookingId();
    r.status = b.getStatus();
    r.passengerId = p.getId();
    r.passengerName = p.getName();
    r.passengerEmail = p.getEmail();
    r.passengerPhone = p.getPhone();
    return r;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public BookingStatus getStatus() {
    return status;
  }

  public Long getPassengerId() {
    return passengerId;
  }

  public String getPassengerName() {
    return passengerName;
  }

  public String getPassengerEmail() {
    return passengerEmail;
  }

  public String getPassengerPhone() {
    return passengerPhone;
  }
}
