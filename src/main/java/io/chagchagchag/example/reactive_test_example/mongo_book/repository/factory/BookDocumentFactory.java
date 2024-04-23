package io.chagchagchag.example.reactive_test_example.mongo_book.repository.factory;

import io.chagchagchag.example.reactive_test_example.mongo_book.repository.entity.BookDocument;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.type.SaleStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class BookDocumentFactory {
  public BookDocument of(ObjectId objectId, String name, BigDecimal price, LocalDateTime publishedAt, SaleStatus saleStatus){
    return new BookDocument(
        objectId, name, price, publishedAt, saleStatus
    );
  }

  public BookDocument newBookDocument(
      String name, BigDecimal price, LocalDateTime publishedAt
  ){
    return of(
        null, name, price, publishedAt, SaleStatus.WAITING_FOR_SALE
    );
  }

  public BookDocument withSaleStatus(
      BookDocument bookDocument, SaleStatus saleStatus
  ){
    return of(
        bookDocument.getId(), bookDocument.getName(), bookDocument.getPrice(), bookDocument.getPublishedAt(), saleStatus
    );
  }
}
