package io.chagchagchag.example.reactive_test_example;


import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;

@Profile("test-mysql")
@EnableR2dbcAuditing
@EnableR2dbcRepositories(
    basePackages = "io.chagchagchag.example.reactive_test_example.healthcheck.repository"
)
@TestConfiguration
public class TestMysqlR2dbcConfig {

  @Bean
  public MySqlConnectionFactory mySqlConnectionFactory(){
    MySqlConnectionConfiguration config = MySqlConnectionConfiguration.builder()
        .host("localhost")
        .port(23306)
        .username("user").password("test1357")
        .database("example")
        .build();

    return MySqlConnectionFactory.from(config);
  }

  @Bean
  public ReactiveTransactionManager transactionManager(MySqlConnectionFactory connectionFactory){
    return new R2dbcTransactionManager(connectionFactory);
  }

  @Bean
  public ConnectionFactoryInitializer initializer(MySqlConnectionFactory connectionFactory){
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    initializer.setDatabasePopulator(populator);
    return initializer;
  }

  @Bean
  public R2dbcEntityTemplate r2dbcEntityTemplate(MySqlConnectionFactory connectionFactory){
    return new R2dbcEntityTemplate(connectionFactory);
  }
}