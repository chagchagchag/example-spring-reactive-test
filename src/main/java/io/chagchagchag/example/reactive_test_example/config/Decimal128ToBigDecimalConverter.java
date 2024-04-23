package io.chagchagchag.example.reactive_test_example.config;

import java.math.BigDecimal;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

public class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {
  @Override
  public BigDecimal convert(Decimal128 source) {
    return source == null ? null : source.bigDecimalValue();
  }
}
