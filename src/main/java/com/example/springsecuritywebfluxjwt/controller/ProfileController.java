package com.example.springsecuritywebfluxjwt.controller;

import com.example.springsecuritywebfluxjwt.controller.dto.ProfileResponse;
import com.example.springsecuritywebfluxjwt.security.CustomAuthentication;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProfileController {

  @GetMapping("/api/me")
  public Mono<?> me(@AuthenticationPrincipal CustomAuthentication authentication) {
    return Mono.just(authentication)
      .map(auth -> new ProfileResponse(
          auth.getPrincipal().toString(),
          auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
          authentication.getJwt())
        );
  }

  @PreAuthorize("hasAuthority('operator')")
  @GetMapping("/api/operator-only")
  public Mono<String> operatorOnly() {
    return Mono.just("Operator page!");
  }

  @PreAuthorize("hasAuthority('admin')")
  @GetMapping("/api/admin-only")
  public Mono<String> adminOnly() {
    return Mono.just("Admin page!");
  }

}

