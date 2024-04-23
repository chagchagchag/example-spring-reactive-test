package io.chagchagchag.example.reactive_test_example.slice_test.data_mongo_test;

import io.chagchagchag.example.reactive_test_example.TestMongodbConfig;
import io.chagchagchag.example.reactive_test_example.mongo_book.BookDocumentService;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.BookDocumentRepository;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.BookDocumentTransactionRepository;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.entity.BookDocument;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.factory.BookDocumentFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test-mongodb")
@ContextConfiguration(
    classes = {
        TestMongodbConfig.class,
        BookDocumentService.class,
        BookDocumentRepository.class,
        BookDocumentTransactionRepository.class,
        BookDocumentFactory.class,
    }
)
@DataMongoTest
public class DataMongoTest_Example1 {

  private static Logger log = LoggerFactory.getLogger(DataMongoTest_Example1.class);

  @Autowired
  private BookDocumentService sut;

  @DisplayName("TEST_새로운_책을_트랜잭셔널_애노테이션을_이용해_저장_및_수정")
  @Test
  public void TEST_새로운_책을_트랜잭셔널_애노테이션을_이용해_저장_및_수정(){
    // given

    // when

    // then
    log.info("before save");
    List<BookDocument> result = sut.insertNewBook("맛도리 여행", BigDecimal.valueOf(3000))
        .toStream()
        .collect(Collectors.toList());
    log.info("after save, result = {}", result);
  }

}
