package io.chagchagchag.example.reactive_test_example;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
}
