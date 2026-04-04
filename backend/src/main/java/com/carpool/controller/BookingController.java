package com.carpool.controller;

import com.carpool.dto.BookingResponse;
import com.carpool.security.CustomUserDetails;
import com.carpool.service.BookingService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping("/book/{rideId}")
  @PreAuthorize("hasRole('PASSENGER')")
  public BookingResponse book(
      @AuthenticationPrincipal CustomUserDetails principal, @PathVariable Long rideId) {
    return bookingService.book(rideId, principal.getId());
  }

  @GetMapping("/my-bookings")
  @PreAuthorize("hasRole('PASSENGER')")
  public List<BookingResponse> myBookings(@AuthenticationPrincipal CustomUserDetails principal) {
    return bookingService.myBookings(principal.getId());
  }

  @DeleteMapping("/cancel-booking/{bookingId}")
  @PreAuthorize("hasRole('PASSENGER')")
  public ResponseEntity<Void> cancelBooking(
      @AuthenticationPrincipal CustomUserDetails principal, @PathVariable Long bookingId) {
    bookingService.cancelBooking(bookingId, principal.getId());
    return ResponseEntity.noContent().build();
  }
}
