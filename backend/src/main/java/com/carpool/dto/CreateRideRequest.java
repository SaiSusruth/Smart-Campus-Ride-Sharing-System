package com.carpool.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateRideRequest {
  @NotBlank private String from_location;

  @NotBlank private String to_location;

  @NotBlank private String date;

  @NotBlank private String time;

  @NotNull @Min(1)
  private Integer available_seats;

  @NotNull @Min(0)
  private BigDecimal price_per_seat;

  public String getFrom_location() {
    return from_location;
  }

  public void setFrom_location(String from_location) {
    this.from_location = from_location;
  }

  public String getTo_location() {
    return to_location;
  }

  public void setTo_location(String to_location) {
    this.to_location = to_location;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getAvailable_seats() {
    return available_seats;
  }

  public void setAvailable_seats(Integer available_seats) {
    this.available_seats = available_seats;
  }

  public BigDecimal getPrice_per_seat() {
    return price_per_seat;
  }

  public void setPrice_per_seat(BigDecimal price_per_seat) {
    this.price_per_seat = price_per_seat;
  }
}
