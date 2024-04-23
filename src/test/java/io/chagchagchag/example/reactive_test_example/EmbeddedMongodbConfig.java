package io.chagchagchag.example.reactive_test_example;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Profile("test-embedded-mongodb")
@EnableReactiveMongoRepositories(
    basePackages = {
        "io.chagchagchag.example.reactive_test_example.mongo_book.repository"
    }
)
@TestConfiguration
public class EmbeddedMongodbConfig {

}
