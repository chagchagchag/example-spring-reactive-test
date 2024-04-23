package io.chagchagchag.example.reactive_test_example.mongo_book;

import io.chagchagchag.example.reactive_test_example.mongo_book.repository.BookDocumentTransactionRepository;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.entity.BookDocument;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.factory.BookDocumentFactory;
import io.chagchagchag.example.reactive_test_example.mongo_book.repository.type.SaleStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookDocumentService {
  private final BookDocumentFactory documentFactory;
  private final BookDocumentTransactionRepository bookDocumentTransactionRepository;
  private final TransactionalOperator transactionalOperator;

  @Transactional
  public Flux<BookDocument> insertNewBook(String name, BigDecimal price){
    BookDocument book = documentFactory.newBookDocument(
        name, price, LocalDateTime.now()
    );

    return bookDocumentTransactionRepository.insert(book)
        .flatMap(bookDocument -> {
          BookDocument forSale = documentFactory.withSaleStatus(bookDocument, SaleStatus.FOR_SALE);
          return bookDocumentTransactionRepository.save(forSale);
        })
        .thenMany(bookDocumentTransactionRepository.findAll());
  }

  public Flux<BookDocument> insertNewBookTransactional(String name, BigDecimal price){
    BookDocument book = documentFactory.newBookDocument(
        name, price, LocalDateTime.now()
    );

    Flux<BookDocument> flux = bookDocumentTransactionRepository.insert(book)
        .flatMap(bookDocument -> {
          BookDocument forSale = documentFactory.withSaleStatus(bookDocument, SaleStatus.FOR_SALE);
          return bookDocumentTransactionRepository.save(forSale);
        })
        .thenMany(bookDocumentTransactionRepository.findAll());

    return transactionalOperator.transactional(flux);
  }

  public Flux<BookDocument> insertNewBookExecute(String name, BigDecimal price){
    BookDocument book = documentFactory.newBookDocument(
        name, price, LocalDateTime.now()
    );

    Flux<BookDocument> flux = bookDocumentTransactionRepository.insert(book)
        .flatMap(bookDocument -> {
          BookDocument forSale = documentFactory.withSaleStatus(bookDocument, SaleStatus.FOR_SALE);
          return bookDocumentTransactionRepository.save(forSale);
        })
        .thenMany(bookDocumentTransactionRepository.findAll());

    return transactionalOperator.execute(status -> flux);
  }
}
