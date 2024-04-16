package io.chagchagchag.example.reactive_test_example.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class HealthcheckController {
  private final HealthcheckService healthcheckService;
  @GetMapping("/healthcheck/ready")
  public Mono<String> getReady(){
    return Mono.just(healthcheckService.ok());
  }
}
