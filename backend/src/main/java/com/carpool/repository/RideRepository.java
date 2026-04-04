package com.carpool.repository;

import com.carpool.entity.Ride;
import com.carpool.entity.RideStatus;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RideRepository extends JpaRepository<Ride, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Ride> findByRideId(Long rideId);

  List<Ride> findByStatus(RideStatus status);

  List<Ride> findByDriver_IdOrderByRideDateDesc(Long driverId);

  @Query(
      "SELECT r FROM Ride r WHERE r.fromLocation = :from AND r.toLocation = :to AND r.rideDate = :d "
          + "AND r.status = 'ACTIVE' AND r.availableSeats > 0")
  List<Ride> searchActive(
      @Param("from") String from, @Param("to") String to, @Param("d") LocalDate date);
}
