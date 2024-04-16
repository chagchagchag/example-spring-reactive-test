package io.chagchagchag.example.reactive_test_example.slice_test.context_configuration.full_scan;

import io.chagchagchag.example.reactive_test_example.ReactiveTestExampleApplication;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ReactiveTestExampleApplication.class})
public class ContextConfigurationTest_Full_Scan {

  @Autowired
  HealthcheckController healthcheckController;

  @Test
  public void TEST_HEALTH_CHECK_CONTROLLER_USING_FULL_SCAN(){
    var expected = "OK";
    StepVerifier.create(healthcheckController.getReady())
        .expectNext(expected)
        .verifyComplete();
  }

}
