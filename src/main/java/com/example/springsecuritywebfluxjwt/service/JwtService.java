package com.example.springsecuritywebfluxjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  private static final long JWT_DURATION = TimeUnit.MINUTES.toMillis(5);
  private static final String JWT_SIGNATURE_SECRET = "secret";
  private static final String CLAIM_ROLES = "roles";

  public String generateToken(String username, List<String> authorities) {
    Date currentDate = new Date();
    long endTime = currentDate.getTime() + JWT_DURATION;
    String jwt = Jwts.builder()
      .setSubject(username)
      .setIssuedAt(currentDate)
      .setExpiration(new Date(endTime))
      .signWith(SignatureAlgorithm.HS256, JWT_SIGNATURE_SECRET)
      .claim(CLAIM_ROLES, authorities.stream().collect(Collectors.joining(",")))
      .compact();
    return jwt;
  }

  public ParsedJwtToken parseToken(String token) {
    Jws<Claims> parsedJwt = Jwts.parser()
      .setSigningKey(JWT_SIGNATURE_SECRET)
      .parseClaimsJws(token);
    List<String> roles = Arrays.asList(parsedJwt.getBody().get(CLAIM_ROLES, String.class).split(","));
    String username = parsedJwt.getBody().getSubject();
    return new ParsedJwtToken(username, roles);
  }
}
