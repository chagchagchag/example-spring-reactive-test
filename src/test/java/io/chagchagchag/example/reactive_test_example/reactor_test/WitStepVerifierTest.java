package io.chagchagchag.example.reactive_test_example.reactor_test;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class WitStepVerifierTest {
  @Test
  public void TEST_FIRST_STEP_SIMPLE_1(){
    Flux<Integer> numbers = Flux.range(0, 11);

    StepVerifier.create(numbers)
        .expectSubscription()
        .expectNext(0,1,2,3,4)
        .expectNextCount(3) // 5,6,7
        .expectNext(8,9,10)
        .verifyComplete();
  }

  @Test
  public void TEST_FIRST_STEP_SIMPLE_2(){
    Flux<Integer> numbers = Flux.range(0, 11);
    var initialRequestSize = 3;

    StepVerifier.create(numbers, initialRequestSize)
        .expectSubscription()
        .expectNext(0, 1, 2).as("first")
        .thenCancel()
        .verify();

    StepVerifier.create(numbers, initialRequestSize)
        .expectNext(0).as("1 of 3")
        .expectNext(1).as("2 of 3")
        .expectNext(2).as("3 of 3")
        .thenCancel()
        .verify();
  }

  @Test
  public void TEST_FIRST_STEP_VERIFIER_OPTIONS(){
    StepVerifierOptions option = StepVerifierOptions.create()
        .initialRequest(3)
        .checkUnderRequesting(false)
        .scenarioName("simple 2");

    Flux<String> message = Flux.just("A","B","C","D","E");

    StepVerifier
        .create(message, option)
        .expectSubscription()
        .expectNext("A","B","C").as("first")
        .thenRequest(2)
        .expectNextCount(2).as("second")
        .expectComplete()
        .verify();

    StepVerifier
        .create(message, option)
        .expectSubscription()
        .expectNext("A","B","C").as("first")
        .thenCancel()
        .verify();
  }

  @Test
  public void TEST_STEP_ON_NEXT_EXAMPLE_1(){
    final Logger log = LoggerFactory.getLogger(WitStepVerifierTest.class);

    Flux<Integer> numbers = Flux.range(0, 8);

    StepVerifier.create(numbers)
        .assertNext(number -> {
          Assertions.assertEquals(0, number);
        })
        .expectNext(1,2)
        .expectNextCount(1)  // 3,4,5,6,7 요소 남은 상태.
                                // 5 개의 요소가 남은 상태에서 1개의 onNext 발생하는지 체크. 결과는 true
                                // onNext 1개 소모하게 됨
                                // 4,5,6,7 남음.
        .expectNextSequence(List.of(4,5,6))
        .expectNextMatches(number -> number == 7)
        .expectComplete()
        .verify();
  }

  @Test
  public void TEST_STEP_EXAMPLE_1(){
    final Logger log = LoggerFactory.getLogger(WitStepVerifierTest.class);

    Flux<Integer> numbers = Flux.range(0, 11);

    StepVerifier.create(numbers, 7)
//        .expectNextCount(7)
        .expectNext(0,1,2,3,4,5,6)
        .as("Total : 7 Elements")
        .then(() -> log.info("아직 4개가 남아있습니다."))
        .thenRequest(4)
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  public void CONTEXT_EXAMPLE(){
    // 1)
    Flux<Integer> numbers = Flux.range(0,11);
    StepVerifier.create(numbers)
        .expectNoAccessibleContext()
        .expectNextCount(11)
        .verifyComplete();

    // 2) Context 를 Flux 에 주입
    Flux<Integer> numbersWithCtx1 = Flux.range(0,11)
        .contextWrite(Context.of("Hello", "World"));
    StepVerifier.create(numbersWithCtx1)
        .expectAccessibleContext()
        .contains("Hello", "World")
        .then()
        .expectNextCount(11)
        .verifyComplete();

    // 3) Context 를 StepVerifier 에 주입
    Flux<Integer> numbersWithoutCtx = Flux.range(0,11);

    var option = StepVerifierOptions.create()
            .withInitialContext(Context.of("Hello", "World"));

    StepVerifier.create(numbersWithoutCtx, option)
        .expectAccessibleContext()
        .contains("Hello", "World")
        .then()
        .expectNextCount(11)
        .verifyComplete();
  }
}
