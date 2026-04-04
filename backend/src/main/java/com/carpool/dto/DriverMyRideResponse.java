package com.carpool.dto;

import com.carpool.entity.Booking;
import com.carpool.entity.Ride;
import com.carpool.entity.RideStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class DriverMyRideResponse {

  @JsonProperty("ride_id")
  private Long rideId;

  @JsonProperty("driver_id")
  private Long driverId;

  @JsonProperty("from_location")
  private String fromLocation;

  @JsonProperty("to_location")
  private String toLocation;

  private String date;
  private String time;

  @JsonProperty("available_seats")
  private int availableSeats;

  @JsonProperty("price_per_seat")
  private BigDecimal pricePerSeat;

  private RideStatus status;

  private List<DriverRideBookingResponse> bookings;

  public static DriverMyRideResponse from(Ride r, List<Booking> bookingsForRide) {
    DriverMyRideResponse dto = new DriverMyRideResponse();
    dto.rideId = r.getRideId();
    dto.driverId = r.getDriver().getId();
    dto.fromLocation = r.getFromLocation();
    dto.toLocation = r.getToLocation();
    dto.date = r.getRideDate().toString();
    dto.time = r.getRideTime().toString();
    dto.availableSeats = r.getAvailableSeats();
    dto.pricePerSeat = r.getPricePerSeat();
    dto.status = r.getStatus();
    dto.bookings =
        bookingsForRide.stream().map(DriverRideBookingResponse::from).toList();
    return dto;
  }

  public Long getRideId() {
    return rideId;
  }

  public Long getDriverId() {
    return driverId;
  }

  public String getFromLocation() {
    return fromLocation;
  }

  public String getToLocation() {
    return toLocation;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public int getAvailableSeats() {
    return availableSeats;
  }

  public BigDecimal getPricePerSeat() {
    return pricePerSeat;
  }

  public RideStatus getStatus() {
    return status;
  }

  public List<DriverRideBookingResponse> getBookings() {
    return bookings;
  }
}
