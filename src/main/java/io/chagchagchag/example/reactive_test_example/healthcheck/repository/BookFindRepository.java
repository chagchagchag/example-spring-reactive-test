package io.chagchagchag.example.reactive_test_example.healthcheck.repository;

import io.chagchagchag.example.reactive_test_example.healthcheck.repository.entity.Book;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface BookFindRepository extends ReactiveSortingRepository<Book, Long> {
  Mono<Book> findFirstByNameOrderByPriceDesc(String name);
}
