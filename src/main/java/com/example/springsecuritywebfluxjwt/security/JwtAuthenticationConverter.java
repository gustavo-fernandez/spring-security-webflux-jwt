package com.example.springsecuritywebfluxjwt.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

  private final String BEARER_PREFIX = "Bearer ";

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.just(exchange.getRequest().getHeaders())
      .filter(httpHeaders -> httpHeaders.containsKey(HttpHeaders.AUTHORIZATION))
      .map(httpHeaders -> httpHeaders.getFirst(HttpHeaders.AUTHORIZATION))
      .filter(authorizationHeader -> authorizationHeader.startsWith(BEARER_PREFIX))
      .map(authorizationHeader -> authorizationHeader.substring(BEARER_PREFIX.length()))
      .map(BearerTokenAuthentication::new);
  }
}
