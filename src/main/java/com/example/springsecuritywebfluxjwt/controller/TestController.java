package com.example.springsecuritywebfluxjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TestController {

  @GetMapping("api/test")
  public Mono<?> test() {
    return Mono.just("Success!");
  }

}
