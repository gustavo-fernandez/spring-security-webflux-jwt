package com.example.springsecuritywebfluxjwt.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HttpRequestsFilter implements WebFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return Mono.just(exchange)
      .map(ex -> {
        var request = ex.getRequest();
        String path = request.getPath().toString();
        String probeType = request.getHeaders().getFirst("probe-type");
        log.info("Path: {}, probe-type: {}", path, probeType);
        return ex;
      })
      .flatMap(ex -> chain.filter(ex));
  }

}
