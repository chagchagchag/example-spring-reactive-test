package io.chagchagchag.example.reactive_test_example.slice_test.web_flux_test;

import io.chagchagchag.example.reactive_test_example.GlobalHealthCheckTestConfiguration;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(
    classes = GlobalHealthCheckTestConfiguration.class
)
@WebFluxTest(controllers = HealthcheckController.class)
public class WebfluxTest_HealthcheckController {
  @MockBean
  HealthcheckService healthcheckService;

  @Autowired
  WebTestClient webTestClient;

  @Test
  public void TEST_HEALTHCHECK_API_OK(){
    var message = "OK";

    Mockito.when(healthcheckService.ok())
        .thenReturn(message);

    webTestClient.get()
        .uri("/healthcheck/ready")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .isEqualTo(message);
  }
}