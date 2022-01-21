package com.example.springsecuritywebfluxjwt.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@EnableReactiveMethodSecurity
public class MySecurityConfig {

  @Bean
  ReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    var user01 = User.builder()
      .username("gustavo")
      .password(passwordEncoder.encode("123456"))
      .authorities("admin")
      .build();
    var user02 = User.builder()
      .username("santiago")
      .password(passwordEncoder.encode("123456"))
      .authorities("operator")
      .build();
    return new MapReactiveUserDetailsService(user01, user02);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityWebFilterChain securityWebFilterChain(
    ServerHttpSecurity http,
    ReactiveAuthenticationManager jwtAuthenticationManager,
    ServerAuthenticationConverter jwtAuthenticationConverter) {
    var webFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
    webFilter.setServerAuthenticationConverter(jwtAuthenticationConverter);

    return http
      .exceptionHandling().authenticationEntryPoint((exchange, ex) -> Mono.fromRunnable(() -> {
        exchange.getResponse().getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
      }))
      .and()
      .addFilterAt(webFilter, SecurityWebFiltersOrder.AUTHENTICATION)
      .formLogin().disable()
      .csrf().disable()
      .httpBasic().disable()
      .authorizeExchange()
      .pathMatchers(POST, "/api/login").permitAll()
      .pathMatchers("/actuator/**").permitAll()
      .pathMatchers(GET, "/open-api/**").permitAll()
      .pathMatchers(GET, "/swagger-ui.html").permitAll()
      .pathMatchers(GET, "/webjars/swagger-ui/**").permitAll()
      .anyExchange().authenticated()
      .and()
      .build();
  }

}
