package com.carpool.service;

import com.carpool.dto.BookingResponse;
import com.carpool.entity.Booking;
import com.carpool.entity.BookingStatus;
import com.carpool.entity.Passenger;
import com.carpool.entity.Ride;
import com.carpool.entity.RideStatus;
import com.carpool.repository.BookingRepository;
import com.carpool.repository.PassengerRepository;
import com.carpool.repository.RideRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final RideRepository rideRepository;
  private final PassengerRepository passengerRepository;

  public BookingService(
      BookingRepository bookingRepository,
      RideRepository rideRepository,
      PassengerRepository passengerRepository) {
    this.bookingRepository = bookingRepository;
    this.rideRepository = rideRepository;
    this.passengerRepository = passengerRepository;
  }

  @Transactional
  public BookingResponse book(Long rideId, Long passengerId) {
    Passenger passenger =
        passengerRepository
            .findById(passengerId)
            .orElseThrow(() -> new IllegalArgumentException("Passenger not found"));
    Ride ride =
        rideRepository
            .findByRideId(rideId)
            .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
    if (ride.getStatus() != RideStatus.ACTIVE) {
      throw new IllegalStateException("Ride is not available");
    }
    if (ride.getDriver().getEmail().equalsIgnoreCase(passenger.getEmail())) {
      throw new IllegalStateException("Cannot book your own ride");
    }
    if (ride.getAvailableSeats() <= 0) {
      throw new IllegalStateException("No seats available");
    }
    ride.setAvailableSeats(ride.getAvailableSeats() - 1);
    rideRepository.save(ride);

    Booking b = new Booking();
    b.setRide(ride);
    b.setPassenger(passenger);
    b.setStatus(BookingStatus.BOOKED);
    b.setRecordedFromLocation(ride.getFromLocation());
    b.setRecordedToLocation(ride.getToLocation());
    b.setRecordedRideDate(ride.getRideDate());
    b.setRecordedRideTime(ride.getRideTime());
    b.setRecordedPricePerSeat(ride.getPricePerSeat());
    b.setRecordedPassengerName(passenger.getName());
    b.setRecordedDriverName(ride.getDriver().getName());
    bookingRepository.save(b);
    return BookingResponse.from(b);
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> myBookings(Long passengerId) {
    Passenger passenger =
        passengerRepository
            .findById(passengerId)
            .orElseThrow(() -> new IllegalArgumentException("Passenger not found"));
    return bookingRepository.findByPassengerOrderByBookingIdDesc(passenger).stream()
        .map(BookingResponse::from)
        .toList();
  }

  @Transactional
  public void cancelBooking(Long bookingId, Long passengerId) {
    Passenger passenger =
        passengerRepository
            .findById(passengerId)
            .orElseThrow(() -> new IllegalArgumentException("Passenger not found"));
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    if (!booking.getPassenger().getId().equals(passenger.getId())) {
      throw new IllegalStateException("Not your booking");
    }
    if (booking.getStatus() != BookingStatus.BOOKED) {
      throw new IllegalStateException("Booking cannot be cancelled");
    }
    Ride ride = booking.getRide();
    if (ride.getStatus() == RideStatus.ACTIVE) {
      ride.setAvailableSeats(ride.getAvailableSeats() + 1);
      rideRepository.save(ride);
    }
    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.save(booking);
  }
}
