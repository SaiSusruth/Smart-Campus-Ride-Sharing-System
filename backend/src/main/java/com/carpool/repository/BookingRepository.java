package com.carpool.repository;

import com.carpool.entity.Booking;
import com.carpool.entity.Passenger;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  @EntityGraph(attributePaths = {"ride", "ride.driver", "passenger"})
  List<Booking> findByPassengerOrderByBookingIdDesc(Passenger passenger);

  /** Explicit JPQL — avoids Spring Data ambiguity with {@code ride.rideId} derived query names. */
  @EntityGraph(attributePaths = {"passenger", "ride"})
  @Query(
      "SELECT b FROM Booking b WHERE b.ride.rideId IN :rideIds ORDER BY b.bookingId ASC")
  List<Booking> findAllForRideIds(@Param("rideIds") Collection<Long> rideIds);
}
