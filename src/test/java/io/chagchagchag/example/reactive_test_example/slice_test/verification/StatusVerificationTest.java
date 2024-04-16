package io.chagchagchag.example.reactive_test_example.slice_test.verification;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    HealthcheckController.class
})
public class StatusVerificationTest {
  @MockBean
  private HealthcheckService healthcheckService;

  @Autowired
  private HealthcheckController healthcheckController;

  @Test
  public void TEST_RESPONSE_IS_OK(){
    WebTestClient webTestClient = WebTestClient
        .bindToController(healthcheckController)
        .build();

    var expected = "OK";
    Mockito.when(healthcheckService.ok())
        .thenReturn("OK");

    webTestClient.get()
        .uri("/healthcheck/ready")
        .exchange()
        .expectStatus()
        .isOk()
        .expectStatus()
        .is2xxSuccessful();
  }
}
