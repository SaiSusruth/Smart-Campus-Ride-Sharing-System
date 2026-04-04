package com.carpool.service;

import com.carpool.dto.CreateRideRequest;
import com.carpool.dto.DriverMyRideResponse;
import com.carpool.dto.RideResponse;
import com.carpool.entity.Booking;
import com.carpool.entity.Driver;
import com.carpool.entity.Ride;
import com.carpool.entity.RideStatus;
import com.carpool.repository.BookingRepository;
import com.carpool.repository.DriverRepository;
import com.carpool.repository.RideRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RideService {

  private final RideRepository rideRepository;
  private final DriverRepository driverRepository;
  private final BookingRepository bookingRepository;

  public RideService(
      RideRepository rideRepository,
      DriverRepository driverRepository,
      BookingRepository bookingRepository) {
    this.rideRepository = rideRepository;
    this.driverRepository = driverRepository;
    this.bookingRepository = bookingRepository;
  }

  @Transactional
  public RideResponse create(Long driverId, CreateRideRequest req) {
    Driver driver =
        driverRepository
            .findById(driverId)
            .orElseThrow(() -> new IllegalArgumentException("Only approved drivers can create rides"));
    Ride ride = new Ride();
    ride.setDriver(driver);
    ride.setFromLocation(req.getFrom_location().trim());
    ride.setToLocation(req.getTo_location().trim());
    ride.setRideDate(parseDate(req.getDate()));
    ride.setRideTime(parseTime(req.getTime()));
    ride.setAvailableSeats(req.getAvailable_seats());
    ride.setPricePerSeat(req.getPrice_per_seat());
    ride.setStatus(RideStatus.ACTIVE);
    rideRepository.save(ride);
    return RideResponse.from(ride);
  }

  private LocalDate parseDate(String s) {
    try {
      return LocalDate.parse(s.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format, use YYYY-MM-DD");
    }
  }

  private LocalTime parseTime(String s) {
    String t = s.trim();
    try {
      if (t.length() == 5) {
        return LocalTime.parse(t, DateTimeFormatter.ofPattern("HH:mm"));
      }
      return LocalTime.parse(t, DateTimeFormatter.ISO_LOCAL_TIME);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid time format, use HH:mm");
    }
  }

  @org.springframework.transaction.annotation.Transactional(readOnly = true)
  public List<RideResponse> allActive() {
    return rideRepository.findByStatus(RideStatus.ACTIVE).stream().map(RideResponse::from).toList();
  }

  @org.springframework.transaction.annotation.Transactional(readOnly = true)
  public List<RideResponse> search(String from, String to, String dateStr) {
    LocalDate d = parseDate(dateStr);
    return rideRepository.searchActive(from.trim(), to.trim(), d).stream()
        .map(RideResponse::from)
        .toList();
  }

  @org.springframework.transaction.annotation.Transactional(readOnly = true)
  public List<DriverMyRideResponse> myRides(Long driverId) {
    List<Ride> rides = rideRepository.findByDriver_IdOrderByRideDateDesc(driverId);
    if (rides.isEmpty()) {
      return List.of();
    }
    List<Long> rideIds = rides.stream().map(Ride::getRideId).toList();
    List<Booking> allBookings = bookingRepository.findAllForRideIds(rideIds);
    Map<Long, List<Booking>> byRideId =
        allBookings.stream()
            .collect(Collectors.groupingBy(b -> b.getRide().getRideId(), Collectors.toList()));
    return rides.stream()
        .map(r -> DriverMyRideResponse.from(r, byRideId.getOrDefault(r.getRideId(), List.of())))
        .toList();
  }

  @Transactional
  public void cancel(Long rideId, Long requesterId, boolean isAdmin) {
    Ride ride =
        rideRepository
            .findByRideId(rideId)
            .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
    if (ride.getStatus() != RideStatus.ACTIVE) {
      throw new IllegalStateException("Ride is not active");
    }
    boolean owner = ride.getDriver().getId().equals(requesterId);
    if (!owner && !isAdmin) {
      throw new IllegalStateException("Not allowed to cancel this ride");
    }
    ride.setStatus(RideStatus.CANCELLED);
    rideRepository.save(ride);
  }
}
