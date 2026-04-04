package com.carpool.controller;

import com.carpool.dto.PendingDriverResponse;
import com.carpool.dto.RejectRequest;
import com.carpool.service.AdminService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/pending-drivers")
  public List<PendingDriverResponse> pendingDrivers() {
    return adminService.pendingDrivers();
  }

  @PutMapping("/verify/{userId}")
  public ResponseEntity<Void> verify(@PathVariable Long userId) {
    adminService.verify(userId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/reject/{userId}")
  public ResponseEntity<Void> reject(
      @PathVariable Long userId, @RequestBody(required = false) RejectRequest body) {
    String remarks = body != null ? body.getRemarks() : null;
    adminService.reject(userId, remarks);
    return ResponseEntity.noContent().build();
  }
}
