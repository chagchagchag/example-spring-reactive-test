package io.chagchagchag.example.reactive_test_example.slice_test.context_configuration.test_configuration;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {
        HealthCheckTestConfiguration.class
    }
)
public class ContextConfigurationTest_Test_Configuration {
  @Autowired
  private HealthcheckController healthcheckController;

  @Test
  public void TEST_HEALTH_CHECK_CONTROLLER_USING_TEST_CONFIGURATION(){
    StepVerifier.create(healthcheckController.getReady())
        .expectNext("OK")
        .verifyComplete();
  }
}
