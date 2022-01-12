package com.example.springsecuritywebfluxjwt.controller;

import com.example.springsecuritywebfluxjwt.controller.dto.LoginRequest;
import com.example.springsecuritywebfluxjwt.controller.dto.LoginResponse;
import com.example.springsecuritywebfluxjwt.service.JwtService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final JwtService jwtService;
  private final ReactiveUserDetailsService reactiveUserDetailsService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("api/login")
  public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return reactiveUserDetailsService.findByUsername(loginRequest.getUsername())
      .map(userDetails -> {
        boolean credentialsOk = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
        if (!credentialsOk) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        var roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String jwt = jwtService.generateToken(loginRequest.getUsername(), roles);
        return new LoginResponse(jwt, "Bearer");
      })
      .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
  }

}
