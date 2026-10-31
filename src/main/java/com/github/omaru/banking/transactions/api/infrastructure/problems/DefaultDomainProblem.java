package com.github.omaru.banking.transactions.api.infrastructure.problems;

import org.springframework.http.HttpStatus;

public class DefaultDomainProblem extends DomainProblem {
  public DefaultDomainProblem(String detail, RuntimeException ex) {
    super(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        detail != null ? detail : "Unexpected server error problem",
        ex);
  }
}
