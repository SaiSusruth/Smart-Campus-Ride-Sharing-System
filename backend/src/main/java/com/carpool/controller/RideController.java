package com.carpool.controller;

import com.carpool.dto.CreateRideRequest;
import com.carpool.dto.DriverMyRideResponse;
import com.carpool.dto.RideResponse;
import com.carpool.security.AccountKind;
import com.carpool.security.CustomUserDetails;
import com.carpool.service.RideService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
public class RideController {

  private final RideService rideService;

  public RideController(RideService rideService) {
    this.rideService = rideService;
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('DRIVER')")
  public RideResponse create(
      @AuthenticationPrincipal CustomUserDetails principal,
      @Valid @RequestBody CreateRideRequest req) {
    if (principal.getAccountKind() != AccountKind.DRIVER) {
      throw new IllegalStateException("Only approved drivers can offer rides");
    }
    return rideService.create(principal.getId(), req);
  }

  @GetMapping("/all")
  public List<RideResponse> all() {
    return rideService.allActive();
  }

  @GetMapping("/my")
  @PreAuthorize("hasRole('DRIVER')")
  public List<DriverMyRideResponse> myRides(@AuthenticationPrincipal CustomUserDetails principal) {
    if (principal.getAccountKind() != AccountKind.DRIVER) {
      throw new IllegalStateException("Only approved drivers have published rides");
    }
    return rideService.myRides(principal.getId());
  }

  @GetMapping("/search")
  public List<RideResponse> search(
      @RequestParam("from") String from,
      @RequestParam("to") String to,
      @RequestParam("date") String date) {
    return rideService.search(from, to, date);
  }

  @PutMapping("/cancel/{rideId}")
  public ResponseEntity<Void> cancel(
      @AuthenticationPrincipal CustomUserDetails principal,
      @PathVariable Long rideId) {
    boolean isAdmin = principal.getAccountKind() == AccountKind.ADMIN;
    rideService.cancel(rideId, principal.getId(), isAdmin);
    return ResponseEntity.noContent().build();
  }
}
