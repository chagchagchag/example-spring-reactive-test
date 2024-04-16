package io.chagchagchag.example.reactive_test_example.slice_test.mock_bean;

import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckController;
import io.chagchagchag.example.reactive_test_example.healthcheck.HealthcheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {HealthcheckController.class}
)
public class MockBeanTest {

  @Autowired
  private HealthcheckController healthcheckController;

  @MockBean
  private HealthcheckService healthcheckService;

  @Test
  public void MOCK_BEAN_TEST(){
    // given
    var expected = "OK";
    Mockito.when(healthcheckService.ok())
        .thenReturn(expected);

    // when
    Mono<String> getReady = healthcheckController.getReady();

    // then
    StepVerifier.create(getReady)
        .expectNext(expected)
        .verifyComplete();
  }

}
