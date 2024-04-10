package io.chagchagchag.example.reactive_test_example;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitAssertionsTest {

  @Test
  public void ASSERT_TRUE_FALSE_SIMPLE(){
    boolean someTrue = true;
    Assertions.assertTrue(someTrue);

    boolean someFalse = false;
    Assertions.assertFalse(someFalse);
  }

  @Test
  public void ASSERT_TRUE_FALSE_SUPPLIER(){
    Assertions.assertTrue(()-> {
      return true;
    });
  }

  @Test
  public void ASSERT_EQUALS_NOT_EQUALS_1(){
    String s1 = "HELLO";
    String s2 = "HELLO";
    Assertions.assertEquals(s1, s2);
  }

  public class Message{
    private String payload;
    public Message(String payload){
      this.payload = payload;
    }
  }

  @Test
  public void ASSERT_EQUALS_NOT_EQUALS_2(){
    Message m1 = new Message("111");
    Message m2 = new Message("222");
    Assertions.assertNotEquals(m1, m2);
  }

  public class Box{
    int weight;
    public Box(int weight){
      this.weight = weight;
    }
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Box box = (Box) o;
      return weight == box.weight;
    }
  }

  @Test
  public void ASSERT_SAME_NOT_SAME(){
    Box b1 = new Box(10);
    Box b2 = new Box(10);
    Assertions.assertNotSame(b1, b2);
    Assertions.assertEquals(b1, b2);

    String s1 = "Hello";
    Supplier<String> lazyS2 = () -> s1;

    Assertions.assertSame(s1, lazyS2.get());
  }

  @Test
  public void ASSERT_ARRAY_EQUALS_AND_ITERABLE_EQUALS(){
    String [] actualArray = {"A", "B", "C"};
    String [] expectedArray = {"A", "B", "C"};
    Assertions.assertArrayEquals(actualArray, expectedArray);

    List<String> actualList = List.of("A","B","C");
    List<String> expectedList = List.of("A","B","C");
    Assertions.assertIterableEquals(actualList, expectedList);
  }

  @Test
  public void ASSERT_LINES_MATCH(){
    Stream<String> sourceStream = Stream.of("A", "B", "C");
    Stream<String> targetStream = Stream.of("A", "B", "C");
    Assertions.assertLinesMatch(sourceStream, targetStream);

    List<String> sourceList = List.of("A", "B", "C");
    List<String> targetList = List.of("A", "B", "C");
    Assertions.assertLinesMatch(sourceList, targetList);
  }

  @Test
  public void ASSERT_NULL_NOT_NULL(){
    String nullStr = null;
    Assertions.assertNull(nullStr);

    String apple = "APPLE";
    Assertions.assertNotNull(apple);
  }

  @Test
  public void ASSERT_INSTANCE_OF(){
    var exception = new RuntimeException("어머, 예외가 발생했어요.");
    Assertions.assertInstanceOf(RuntimeException.class, exception);
  }

  @Test
  public void ASSERT_FAIL(){
    Logger log = LoggerFactory.getLogger(JUnitAssertionsTest.class);
    var exception = new RuntimeException("어머, 예외가 발생했어요.");

    if(exception != null)
      Assertions.fail();
    else{
      log.info("예외가 발생하지 않았어요. 정상이에요.");
    }
  }

  @Test
  public void ASSERT_TEST_MUST_FAIL(){
    Logger log = LoggerFactory.getLogger(JUnitAssertionsTest.class);
    var exception = new RuntimeException("어머, 예외가 발생했어요.");

    if(exception != null)
      Assertions.fail(exception);
    else{
      log.info("예외가 발생하지 않았어요. 정상이에요.");
    }
  }

  @Test
  public void ASSERT_ALL(){
    Assertions.assertAll(
        () -> {Assertions.assertTrue(true);},
        () -> {Assertions.assertTrue(1 > 0);},
        () -> {Assertions.assertTrue(List.of(1,2,3).size() == 3);}
    );

    Stream<Executable> stream = Stream.of(
        () -> {Assertions.assertTrue(true);},
        () -> {Assertions.assertTrue(1 > 0);},
        () -> {Assertions.assertTrue(List.of(1,2,3).size() == 3);}
    );
    Assertions.assertAll(stream);
  }

  @Test
  public void ASSERT_THROWS_ASSERT_THROWS_EXACTLY(){
    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          throw new IllegalStateException("예외가 발생했어요");
        }
    );

    Assertions.assertThrows(
        RuntimeException.class, // IllegalArgumentException 은 RuntimeException 의 한 종류로 판단
        () -> {
          throw new IllegalStateException("예외가 발생했어요");
        }
    );

    /** 테스트가 실패합니다.
     * RuntimeException 은 IllegalStateException 의 상위타입입니다.
    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          throw new RuntimeException("asdf");
        }
    );
    */

    Assertions.assertThrowsExactly(
        IllegalStateException.class,
        () -> {
          throw new IllegalStateException("예외가 발생했어요");
        }
    );

    /** 테스트가 실패합니다.
     * IllegalStateException RuntimeException 의 한 종류이지만,
     * Assertions.assertThrowsExactly 는 정확하게 타입이 일치해야만 성공으로 인식합니다.
    Assertions.assertThrowsExactly(
        RuntimeException.class,
        () -> {
          throw new IllegalStateException("예외가 발생했어요");
        }
    );
    */
  }

  @Test
  public void ASSERT_DOES_NOT_THROW(){
    Assertions.assertDoesNotThrow(
        () -> {}
    );

    Assertions.assertDoesNotThrow(
        () -> "안녕하세요"
    );
  }

  @Test
  public void ASSERT_TIMOUT(){
    final Duration timeLimit = Duration.ofSeconds(1);

    Assertions.assertTimeout(timeLimit, () -> {
      sleep(500);
    });

    var externalResult = Assertions.assertTimeout(timeLimit, () -> {
      sleep(500);
      return true;
    });
    Assertions.assertTrue(externalResult);

    /** 실패하는 케이스 : 미리 지정한 1초의 타임아웃을 넘어서는 케이스
    Assertions.assertTimeout(timeLimit, () -> {
      sleep(2000);
    });
    */
  }

  public void sleep(long ms){
    try{
      Thread.sleep(ms);
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  public void ASSERT_TIMEOUT_NO_PREEMPTIVELY(){
    final Duration timeLimit = Duration.ofSeconds(8);

    var externalResult = Assertions.assertTimeout(timeLimit, () -> {
      sleep(7000);
      return true;
    });
    Assertions.assertTrue(externalResult);
  }

  @Disabled
  @TestMustFail
  public void ASSERT_TIMEOUT_PREEMPTIVELY(){
    final Duration timeLimit = Duration.ofSeconds(1);

    var externalResult = Assertions.assertTimeoutPreemptively(timeLimit, () -> {
      sleep(7000);
      return true;
    });
    Assertions.assertTrue(externalResult);
  }
}
