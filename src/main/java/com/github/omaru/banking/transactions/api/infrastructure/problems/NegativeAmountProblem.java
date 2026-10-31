package com.github.omaru.banking.transactions.api.infrastructure.problems;

import com.github.omaru.banking.transactions.api.domain.exceptions.NegativeAmountException;
import org.springframework.http.HttpStatus;

public class NegativeAmountProblem extends DomainProblem {
  public NegativeAmountProblem(NegativeAmountException ex) {
    super(HttpStatus.BAD_REQUEST.value(), "Negative Amount for Account", ex.getMessage(), ex);
  }
}
