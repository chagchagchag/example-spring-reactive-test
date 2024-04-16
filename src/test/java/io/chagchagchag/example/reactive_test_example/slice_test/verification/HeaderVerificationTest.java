package io.chagchagchag.example.reactive_test_example.slice_test.verification;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HealthcheckController.class)
public class HeaderVerificationTest {
  @MockBean
  private HealthcheckService healthcheckService;
  @Autowired
  private HealthcheckController healthcheckController;

  @Test
  public void TEST_HEADER(){
    // given
    WebTestClient webTestClient = WebTestClient
        .bindToController(healthcheckController)
        .build();

    String expected = "OK";

    // when
    Mockito.when(healthcheckService.ok())
        .thenReturn("OK");

    // then
    webTestClient.get()
        .uri("/healthcheck/ready/header")
        .exchange()
        .expectHeader()
        .contentType("text/plain;charset=UTF-8")
        .expectHeader()
        .exists("brand")
        .expectHeader()
        .value("category", categoryHeaderValue -> {
          Assertions.assertThat(categoryHeaderValue).isEqualTo("shoes");
        })
        .expectHeader()
        .doesNotExist("gender")
        .expectHeader()
        .valueEquals("email", "abc@gmail.com");

  }
}
