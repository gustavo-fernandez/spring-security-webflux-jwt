package com.example.springsecuritywebfluxjwt.security;

import java.util.List;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthentication extends AbstractAuthenticationToken {

  @Getter
  private String jwt;
  private String username;

  public CustomAuthentication(String username, String jwt, List<GrantedAuthority> authorities) {
    super(authorities);
    this.username = username;
    this.jwt = jwt;
    this.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return username;
  }
}
