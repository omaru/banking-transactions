package com.github.omaru.banking.transactions.api.infrastructure.problems;

import com.github.omaru.banking.transactions.api.domain.exceptions.InsufficientBalanceException;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceProblem extends DomainProblem {
  public InsufficientBalanceProblem(InsufficientBalanceException ex) {
    super(HttpStatus.BAD_REQUEST.value(), "Insufficient Balance for Account", ex.getMessage(), ex);
  }
}
