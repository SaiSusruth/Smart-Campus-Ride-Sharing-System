package com.carpool.controller;

import com.carpool.dto.LoginRequest;
import com.carpool.dto.RegisterRequest;
import com.carpool.dto.UserResponse;
import com.carpool.security.CustomUserDetails;
import com.carpool.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  public AuthController(
      AuthService authService,
      AuthenticationManager authenticationManager,
      SecurityContextRepository securityContextRepository) {
    this.authService = authService;
    this.authenticationManager = authenticationManager;
    this.securityContextRepository = securityContextRepository;
  }

  @PostMapping("/register")
  public UserResponse register(@Valid @RequestBody RegisterRequest req) {
    return authService.register(req);
  }

  @PostMapping("/login")
  public UserResponse login(
      @Valid @RequestBody LoginRequest req,
      HttpServletRequest request,
      HttpServletResponse response) {
    String email = req.getEmail().trim().toLowerCase();
    authenticateSession(email, req.getPassword(), request, response);
    CustomUserDetails principal =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return authService.toResponse(principal);
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> me() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null
        || !auth.isAuthenticated()
        || !(auth.getPrincipal() instanceof CustomUserDetails principal)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(authService.toResponse(principal));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return ResponseEntity.noContent().build();
  }

  private void authenticateSession(
      String email, String rawPassword, HttpServletRequest request, HttpServletResponse response) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, rawPassword));
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);
    securityContextRepository.saveContext(context, request, response);
  }
}
