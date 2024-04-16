package io.chagchagchag.example.reactive_test_example.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class HealthcheckController {
  private final HealthcheckService healthcheckService;
  @GetMapping("/healthcheck/ready")
  public Mono<String> getReady(){
    return Mono.just(healthcheckService.ok());
  }

  @GetMapping("/healthcheck/ready/header")
  public Mono<ResponseEntity<String>> getReadyWithHeader(){
    return Mono.just(healthcheckService.ok())
        .map(okMessage -> ResponseEntity.ok()
            .header("brand", "nike")
            .header("category", "shoes")
            .header("email", "abc@gmail.com")
            .body(okMessage)
        );
  }

  @GetMapping("/healthcheck/ready/cookie")
  public Mono<String> getReadyWithCookie(
      ServerWebExchange exchange
  ){
    ResponseCookie cookie = ResponseCookie.from("brand", "nike")
        .maxAge(300)
        .domain("youtube.com")
        .httpOnly(true)
        .path("/")
        .sameSite("None")
        .secure(true)
        .build();

    exchange.getResponse().addCookie(cookie);
    return Mono.just(healthcheckService.ok());
  }

  static record ReadyStatusBody(
      String message
  ){}

  @GetMapping("/healthcheck/ready/body")
  public Mono<ReadyStatusBody> readyWithBody(){
    return Mono.just(healthcheckService.ok())
        .map(message -> new ReadyStatusBody(message));
  }
}
