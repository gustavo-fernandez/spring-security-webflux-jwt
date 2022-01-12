package com.example.springsecuritywebfluxjwt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class MySecurityConfig {

  @Bean
  ReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    var user01 = User.builder()
      .username("gustavo")
      .password(passwordEncoder.encode("123456"))
      .authorities("operator")
      .build();
    var user02 = User.builder()
      .username("santiago")
      .password(passwordEncoder.encode("123456"))
      .authorities("admin")
      .build();
    return new MapReactiveUserDetailsService(user01, user02);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
