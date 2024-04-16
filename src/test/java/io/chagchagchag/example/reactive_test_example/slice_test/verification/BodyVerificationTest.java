package io.chagchagchag.example.reactive_test_example.slice_test.verification;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HealthcheckController.class)
public class BodyVerificationTest {
  @MockBean
  private HealthcheckService healthcheckService;
  @Autowired
  private HealthcheckController healthcheckController;

  static record ReadyStatusBody(
      String message
  ){}

  @Test
  public void TEST_BODY(){
    String expected = "OK";
    WebTestClient webTestClient = WebTestClient
        .bindToController(healthcheckController)
        .build();

    Mockito.when(healthcheckService.ok())
        .thenReturn("OK");

    ReadyStatusBody expectedBody = new ReadyStatusBody(expected);

    webTestClient.get()
        .uri("/healthcheck/ready/body")
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(ReadyStatusBody.class)
        .isEqualTo(expectedBody)
        .value(r -> {
          Assertions.assertThat(r.message).isEqualTo(expected);
        });
  }

}
