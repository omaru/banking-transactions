package com.github.omaru.banking.transactions.api.infrastructure.config;

import com.github.omaru.banking.transactions.api.infrastructure.problems.DomainProblemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainExceptionConfig {

  @Bean
  public DomainProblemMapper domainProblemMapper() {
    return new DomainProblemMapper();
  }
}
