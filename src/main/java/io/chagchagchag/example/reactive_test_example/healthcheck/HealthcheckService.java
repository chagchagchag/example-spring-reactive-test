package io.chagchagchag.example.reactive_test_example.healthcheck;

import org.springframework.stereotype.Service;

@Service
public class HealthcheckService {
  public String ok(){
    return "OK";
  }
}
