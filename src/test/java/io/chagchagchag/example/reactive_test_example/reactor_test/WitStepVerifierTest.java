package io.chagchagchag.example.reactive_test_example.reactor_test;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

  //-- expectError
  @Test
  public void SIMPLE_EXPECT_ERROR(){
    // 1) 단순 검증
    var errorFlux1 = Flux.error(new IllegalArgumentException());
    StepVerifier.create(errorFlux1)
        .expectError()
        .verify();

    // 2) 예외 타입 검증
    var errorFlux2 = Flux.error(new IllegalArgumentException());
    StepVerifier.create(errorFlux2)
        .expectError(IllegalArgumentException.class)
        .verify();

    // 3) 예외 메시지 검증
    String message = "웁스, 에러에요.";
    var errorFlux3 = Flux.error(new IllegalArgumentException(message));
    StepVerifier.create(errorFlux3)
        .expectErrorMessage(message)
        .verify();

    // 4) expectErrorMatches
    String message2 = "웁스, 에러에요";
    var errorFlux4 = Flux.error(new IllegalArgumentException(message2));
    StepVerifier.create(errorFlux4)
        .expectErrorMatches(e -> {
          return e instanceof IllegalArgumentException && e.getMessage().equals(message2);
        })
        .verify();

    // 5) expectErrorSatisfies
    String message3 = "웁스, 에러네요";
    var errorFlux5 = Flux.error(new IllegalArgumentException(message3));
    StepVerifier.create(errorFlux5)
        .expectErrorSatisfies(e -> {
          Assertions.assertInstanceOf(IllegalArgumentException.class, e);
          Assertions.assertEquals(message3, e.getMessage());
        })
        .verify();
  }


  //-- expectTimeout
  @Test
  public void EXPECT_TIMEOUT_TEST(){
    // 1)
    var publisher1 = Mono.delay(Duration.ofMillis(300));
    StepVerifier.create(publisher1)
        .expectTimeout(Duration.ofMillis(50))
        .verify();

    // 2) 아래 코드는 에러가 나야 정상인 코드
//    var publisher2 = Mono.delay(Duration.ofMillis(200));
//    StepVerifier.create(publisher2)
//        .expectTimeout(Duration.ofSeconds(1))
//        .verify();

    // 3)
    var publisher3 = Flux.range(0, 10)
        .delayElements(Duration.ofMillis(500));
    StepVerifier.create(publisher3)
        .expectTimeout(Duration.ofMillis(100))
        .verify();
  }

  //-- expectComplete
  @Test
  public void EXPECT_COMPLETE_TEST(){
    // 1)
    var publisher1 = Flux.just("Hello", "World", "Great");
    StepVerifier.create(publisher1)
        .expectNextCount(3)
        .expectComplete()
        .verify();

    // 2) 아래 코드는 Exception 이 발생하는 코드에 대해 expectError** 을 하지 않았으므로 에러가 발생합니다.
//    var publisher2 = Flux.error(new IllegalArgumentException());
//    StepVerifier.create(publisher2)
//        .expectComplete()
//        .verify();
  }

  //-- thenCancel
  @Test
  public void THEN_CANCEL(){
    var publisher = Flux.range(1, Integer.MAX_VALUE)
        .doOnNext(number -> System.out.println("안녕하세요"));

    StepVerifier.create(publisher)
        .expectNextCount(2)
        .thenCancel()
        .verify();
  }

  //-- verify*** 메서드
  @Test
  public void VERIFY_METHODS(){
    var message = "안녕하십니까?";
    var err = new IllegalArgumentException(message);
    StepVerifier.create(Mono.error(err))
        .verifyErrorMessage(message);

    StepVerifier.create(Mono.just(3000))
        .expectNext(3000)
        .verifyComplete();

    var mono = Mono.delay(Duration.ofMillis(300));
    StepVerifier.create(mono)
        .verifyTimeout(Duration.ofMillis(100));
  }

  @Test
  public void VERIFY_METHOD(){
    var publisher = Flux.just("Hello", "World", "Great");

    StepVerifier.create(publisher)
        .expectNextCount(3)
        .expectComplete()
        .log()
        .verify();
  }

  @Test
  public void VERIFY_THEN_ASSERT_THAT(){
    var delay = Duration.ofSeconds(1);
    StepVerifier.create(Flux.just("Hello", "World", "Great"))
        .expectNextCount(3)
        .expectComplete()
        .log()
        .verifyThenAssertThat(delay)
        .tookLessThan(delay)
        .hasNotDroppedElements();
  }

  @Test
  public void WITH_VIRTUAL_TIME_DELAY_AND_WAI_1(){
    StepVerifier.withVirtualTime(() -> {
          return Flux.just("Hello", "World", "Great")
              .delayElements(Duration.ofSeconds(1));
        })
        .thenAwait(Duration.ofSeconds(2))
        .expectNextCount(2)
        .thenAwait(Duration.ofSeconds(1))
        .expectNextCount(1)
        .verifyComplete();
  }

  @Test
  public void WITH_VIRTUAL_TIME_DELAY_AND_WAI_2(){
    var longDelayPublisher = Flux.just("Hello", "World", "Great")
        .delayElements(Duration.ofSeconds(1));

    StepVerifier.withVirtualTime(() -> longDelayPublisher)
        .thenAwait(Duration.ofSeconds(2))
        .expectNextCount(2)
        .thenAwait(Duration.ofSeconds(1))
        .expectNextCount(1)
        .verifyComplete();
  }

  @Test
  public void WITH_VIRTUAL_TIME_3_DAY_DELAY(){
    StepVerifier
        .withVirtualTime(() -> {
          return Flux.just("Hello", "World", "Great")
              .delayElements(Duration.ofDays(1));
        })
        .expectSubscription()
        .thenAwait(Duration.ofDays(1))
        .expectNextCount(1)
        .thenAwait(Duration.ofDays(2))
        .expectNextCount(2)
        .verifyComplete();
  }
}
