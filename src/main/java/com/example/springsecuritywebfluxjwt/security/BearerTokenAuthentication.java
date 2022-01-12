package com.example.springsecuritywebfluxjwt.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class BearerTokenAuthentication extends AbstractAuthenticationToken {

  @Getter
  private String token;

  public BearerTokenAuthentication(String token) {
    super(AuthorityUtils.NO_AUTHORITIES);
    this.token = token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }
}
