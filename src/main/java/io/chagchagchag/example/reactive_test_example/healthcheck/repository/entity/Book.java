package io.chagchagchag.example.reactive_test_example.healthcheck.repository.entity;

import io.chagchagchag.example.reactive_test_example.healthcheck.repository.type.SaleStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Book {
  @Id
  private Long id;
  private String name;
  private BigDecimal price;
  private LocalDateTime publishedAt;
  @Column("sale_status")
  private SaleStatus saleStatus;
  private Long authorId;
}
