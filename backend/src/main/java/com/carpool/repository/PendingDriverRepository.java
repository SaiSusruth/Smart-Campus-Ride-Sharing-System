package com.carpool.repository;

import com.carpool.entity.PendingAccountStatus;
import com.carpool.entity.PendingDriver;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingDriverRepository extends JpaRepository<PendingDriver, Long> {
  Optional<PendingDriver> findByEmail(String email);

  List<PendingDriver> findByAccountStatusOrderByIdAsc(PendingAccountStatus status);

  boolean existsByEmailAndAccountStatus(String email, PendingAccountStatus status);
}
