package io.chagchagchag.example.reactive_test_example.mongo_book.repository;

import io.chagchagchag.example.reactive_test_example.mongo_book.repository.entity.BookDocument;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BookDocumentRepository extends ReactiveSortingRepository<BookDocument, ObjectId> {

}
