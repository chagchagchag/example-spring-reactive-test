package io.chagchagchag.example.reactive_test_example;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GlobalHealthCheckTestConfiguration {
  @Bean
  public HealthcheckService healthcheckService(){
    return new HealthcheckService();
  }

  @Bean
  public HealthcheckController healthcheckController(
      HealthcheckService healthcheckService
  ){
    return new HealthcheckController(healthcheckService);
  }
}
