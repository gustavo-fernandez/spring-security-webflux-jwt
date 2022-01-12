package com.example.springsecuritywebfluxjwt.security;

import com.example.springsecuritywebfluxjwt.service.JwtService;
import com.example.springsecuritywebfluxjwt.service.ParsedJwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtService jwtService;

  @Override
  public Mono<Authentication> authenticate(Authentication jwtAuthentication) {
    return Mono.just(jwtAuthentication)
      .cast(BearerTokenAuthentication.class)
      .map(bearerTokenAuthentication -> {
        String jwt = bearerTokenAuthentication.getToken();
        ParsedJwtToken parsedJwtToken = jwtService.parseToken(jwt);
        var roles = parsedJwtToken.getRoles();
        var rolesAsArray = roles.toArray(new String[roles.size()]);
        var authorities = AuthorityUtils.createAuthorityList(rolesAsArray);
        return new CustomAuthentication(parsedJwtToken.getUsername(), jwt, authorities);
      });
  }
}
