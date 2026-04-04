package com.carpool.dto;

import com.carpool.entity.Booking;
import com.carpool.entity.BookingStatus;
import com.carpool.entity.Driver;
import com.carpool.entity.Passenger;
import com.carpool.entity.Ride;
import com.carpool.entity.RideStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BookingResponse {

	@JsonProperty("booking_id")
	private Long bookingId;

	@JsonProperty("ride_id")
	private Long rideId;

	private BookingStatus status;

	@JsonProperty("from_location")
	private String fromLocation;

	@JsonProperty("to_location")
	private String toLocation;

	private String date;
	private String time;

	@JsonProperty("price_per_seat")
	private BigDecimal pricePerSeat;

	@JsonProperty("ride_status")
	private RideStatus rideStatus;

	@JsonProperty("driver_id")
	private Long driverId;

	@JsonProperty("driver_name")
	private String driverName;

	@JsonProperty("driver_phone")
	private String driverPhone;

	@JsonProperty("vehicle_number")
	private String vehicleNumber;

	@JsonProperty("registration_number")
	private String registrationNumber;

	@JsonProperty("passenger_id")
	private Long passengerId;

	@JsonProperty("passenger_name")
	private String passengerName;

	@JsonProperty("passenger_email")
	private String passengerEmail;

	@JsonProperty("passenger_phone")
	private String passengerPhone;

	public static BookingResponse from(Booking b) {
		BookingResponse r = new BookingResponse();
		r.bookingId = b.getBookingId();
		r.status = b.getStatus();

		Ride ride = b.getRide();
		Passenger p = b.getPassenger();
		Driver driver = ride.getDriver();

		r.rideId = ride.getRideId();
		r.rideStatus = ride.getStatus();
		r.driverId = driver.getId();
		r.driverPhone = driver.getPhone();
		r.vehicleNumber = driver.getVehicleNumber();
		r.registrationNumber = driver.getRegistrationNumber();
		r.passengerId = p.getId();
		r.passengerEmail = p.getEmail();
		r.passengerPhone = p.getPhone();

		if (b.getRecordedFromLocation() != null) {
			r.fromLocation = b.getRecordedFromLocation();
			r.toLocation = b.getRecordedToLocation();
			r.date = b.getRecordedRideDate() != null ? b.getRecordedRideDate().toString()
					: ride.getRideDate().toString();
			r.time = b.getRecordedRideTime() != null ? b.getRecordedRideTime().toString()
					: ride.getRideTime().toString();
			r.pricePerSeat = b.getRecordedPricePerSeat() != null ? b.getRecordedPricePerSeat() : ride.getPricePerSeat();
			r.driverName = b.getRecordedDriverName() != null ? b.getRecordedDriverName() : driver.getName();
			r.passengerName = b.getRecordedPassengerName() != null ? b.getRecordedPassengerName() : p.getName();
		} else {
			r.fromLocation = ride.getFromLocation();
			r.toLocation = ride.getToLocation();
			r.date = ride.getRideDate().toString();
			r.time = ride.getRideTime().toString();
			r.pricePerSeat = ride.getPricePerSeat();
			r.driverName = driver.getName();
			r.passengerName = p.getName();
		}

		return r;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public Long getRideId() {
		return rideId;
	}

	public BookingStatus getStatus() {
		return status;
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

	public BigDecimal getPricePerSeat() {
		return pricePerSeat;
	}

	public RideStatus getRideStatus() {
		return rideStatus;
	}

	public Long getDriverId() {
		return driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
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
