package com.carpool.config;

import com.carpool.entity.Admin;
import com.carpool.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminBootstrapConfig {

  @Bean
  CommandLineRunner ensureDefaultAdmin(
      AdminRepository adminRepository,
      PasswordEncoder passwordEncoder,
      @Value("${app.admin.default.name:Default Admin}") String adminName,
      @Value("${app.admin.default.email:admin@smartcampus.local}") String adminEmail,
      @Value("${app.admin.default.password:admin123}") String adminPassword,
      @Value("${app.admin.default.phone:0000000000}") String adminPhone) {
    return args -> {
      String normalized = adminEmail.trim().toLowerCase();
      if (adminRepository.existsByEmail(normalized)) {
        return;
      }
      Admin admin = new Admin();
      admin.setName(adminName);
      admin.setEmail(normalized);
      admin.setPhone(adminPhone);
      admin.setPassword(passwordEncoder.encode(adminPassword));
      adminRepository.save(admin);
    };
  }
}
