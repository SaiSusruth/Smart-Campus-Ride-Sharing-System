package com.carpool.repository;

import com.carpool.entity.Driver;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
  Optional<Driver> findByEmail(String email);

  boolean existsByEmail(String email);
}
