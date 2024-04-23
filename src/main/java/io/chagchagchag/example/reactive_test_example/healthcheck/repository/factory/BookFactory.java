package io.chagchagchag.example.reactive_test_example.healthcheck.repository.factory;

import io.chagchagchag.example.reactive_test_example.healthcheck.repository.entity.Book;
import io.chagchagchag.example.reactive_test_example.healthcheck.repository.type.SaleStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class BookFactory {
  public Book of(Long id, String name, BigDecimal price, LocalDateTime publishedAt, SaleStatus saleStatus, Long authorId){
    return new Book(id, name, price, publishedAt, saleStatus, authorId);
  }

  public Book newBook(String name, BigDecimal price, LocalDateTime publishedAt, Long authorId){
    return of(null, name, price, publishedAt, SaleStatus.WAITING_FOR_SALE, authorId);
  }

  public Book withSaleStatus(Book book, SaleStatus saleStatus){
    return of(book.getId(), book.getName(), book.getPrice(), book.getPublishedAt(), saleStatus, book.getAuthorId());
  }
}
