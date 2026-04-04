package com.carpool.security;

import com.carpool.entity.Role;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private final AccountKind accountKind;
  private final Long id;
  private final String email;
  private final String passwordHash;
  private final Role role;

  public CustomUserDetails(
      AccountKind accountKind, Long id, String email, String passwordHash, Role role) {
    this.accountKind = accountKind;
    this.id = id;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  public AccountKind getAccountKind() {
    return accountKind;
  }

  public Long getId() {
    return id;
  }

  public Role getRole() {
    return role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
