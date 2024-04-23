package io.chagchagchag.example.reactive_test_example.mongo_book.repository;

import io.chagchagchag.example.reactive_test_example.mongo_book.repository.entity.BookDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookDocumentTransactionRepository extends ReactiveMongoRepository<BookDocument, ObjectId> {
}
