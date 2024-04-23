package io.chagchagchag.example.reactive_test_example;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.chagchagchag.example.reactive_test_example.config.BigDecimalToDecimal128Converter;
import io.chagchagchag.example.reactive_test_example.config.Decimal128ToBigDecimalConverter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(
    basePackages = {
        "io.chagchagchag.example.reactive_test_example.mongo_book.repository"
    },
    reactiveMongoTemplateRef = "helloworldReactiveMongoTemplate"
)
@TestConfiguration
public class TestMongodbConfig {
  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Bean
  public MongoClient reactiveMongoClient(){
    return MongoClients.create(mongoUri);
  }

  @Bean
  public ReactiveMongoTransactionManager transactionManager(
      ReactiveMongoDatabaseFactory databaseFactory
  ){
    return new ReactiveMongoTransactionManager(databaseFactory);
  }

  @Bean(name = "helloworldReactiveMongoDatabaseFactory")
  public SimpleReactiveMongoDatabaseFactory helloworldReactiveMongoDatabaseFactory(
      MongoProperties mongoProperties,
      MongoClient mongoClient
  ){
    final String database = "helloworld";
    return new SimpleReactiveMongoDatabaseFactory(mongoClient, database);
  }

  @Bean(name = "helloworldReactiveMongoTemplate")
  public ReactiveMongoTemplate helloworldReactiveMongoTemplate(
      ReactiveMongoDatabaseFactory helloworldReactiveMongoDatabaseFactory,
      MongoConverter mongoConverter
  ){
    return new ReactiveMongoTemplate(helloworldReactiveMongoDatabaseFactory, mongoConverter);
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions(){
    return new MongoCustomConversions(
        Arrays.asList(
            new BigDecimalToDecimal128Converter(),
            new Decimal128ToBigDecimalConverter()
        )
    );
  }
}
