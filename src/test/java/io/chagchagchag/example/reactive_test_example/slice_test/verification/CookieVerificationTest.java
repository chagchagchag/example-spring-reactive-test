package io.chagchagchag.example.reactive_test_example.slice_test.verification;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HealthcheckController.class)
public class CookieVerificationTest {
  @MockBean
  private HealthcheckService healthcheckService;
  @Autowired
  private HealthcheckController healthcheckController;

  @Test
  public void TEST_COOKIE(){
    // given
    String expected = "OK";
    WebTestClient webTestClient = WebTestClient.bindToController(healthcheckController)
        .build();

    // when
    Mockito.when(healthcheckService.ok())
        .thenReturn(expected);

    // then
    var cookieName = "brand";
    webTestClient.get()
        .uri("/healthcheck/ready/cookie")
        .exchange()
        .expectCookie().exists(cookieName)
        .expectCookie().valueEquals(cookieName, "nike")
        .expectCookie().domain(cookieName,"youtube.com")
        .expectCookie().httpOnly(cookieName,true)
        .expectCookie().path(cookieName, "/")
        .expectCookie().secure(cookieName, true)
        .expectCookie().maxAge(cookieName, Duration.ofSeconds(300));
  }
}
