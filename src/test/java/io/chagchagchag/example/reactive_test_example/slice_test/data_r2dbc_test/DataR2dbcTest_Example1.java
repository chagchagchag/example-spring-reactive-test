package io.chagchagchag.example.reactive_test_example.slice_test.data_r2dbc_test;

import io.chagchagchag.example.reactive_test_example.TestMysqlR2dbcConfig;
import io.chagchagchag.example.reactive_test_example.healthcheck.repository.BookFindRepository;
import io.chagchagchag.example.reactive_test_example.healthcheck.repository.entity.Book;
import io.chagchagchag.example.reactive_test_example.healthcheck.repository.factory.BookFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ActiveProfiles("test-mysql")
@Import(TestMysqlR2dbcConfig.class)
@DataR2dbcTest
public class DataR2dbcTest_Example1 {
  @Autowired
  BookFindRepository bookFindRepository;

  @Autowired
  R2dbcEntityTemplate r2dbcEntityTemplate;

  BookFactory bookFactory = new BookFactory();

  @Test
  public void SAVE_ENTITY_TEST(){
    Book book = bookFactory.newBook(
        "레버리지", BigDecimal.valueOf(16200), LocalDateTime.now(), 1L
    );

    Mono<Book> insertFlux = r2dbcEntityTemplate.insert(book);

    StepVerifier.create(insertFlux)
        .assertNext(it -> {
          Assertions.assertThat(it.getName()).isEqualTo(book.getName());
          Assertions.assertThat(it.getPrice()).isEqualTo(book.getPrice());
          Assertions.assertThat(it.getPublishedAt()).isEqualTo(book.getPublishedAt());
          Assertions.assertThat(it.getAuthorId()).isEqualTo(book.getAuthorId());
        })
        .verifyComplete();
  }
}
