package io.chagchagchag.example.reactive_test_example.reactor_test;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class WithoutStepVerifierTest {
  @Test
  public void TEST_FLUX_1_BY_TO_LIST_BLOCK(){
    Flux<Integer> numbers = Flux.create(sink -> {
      for(int i=0; i<10; i++) sink.next(i);
      sink.complete();
    });

    var expectedList = IntStream.range(0, 10)
        .boxed()
        .collect(Collectors.toList());

    var actualList = numbers.collectList().block();

    Assertions.assertIterableEquals(expectedList, actualList);
  }

  @Test
  public void TEST_FLUX_2_THROW_EXCEPTION_STATEMENT(){
    Flux<Integer> numbers = Flux.create(sink -> {
      for(int i=0; i<10; i++) {
        sink.next(i);
        if(i==5) sink.error(new RuntimeException("잠깐 멈춰봐요"));
      }
      sink.complete();
    });

    Assertions.assertThrows(RuntimeException.class, () -> {
      numbers.collectList().block();
    });
  }

  @Test
  public void TEST_FLUX_3_HEAVY_JOB_TEST(){
    Flux<Integer> numbers = Flux.range(0, 10)
        .delayElements(Duration.ofSeconds(2));

    var expectedList = IntStream.range(0, 10)
        .boxed()
        .collect(Collectors.toList());

    var actualList = numbers.collectList().block();

    Assertions.assertIterableEquals(expectedList, actualList);
  }
}
