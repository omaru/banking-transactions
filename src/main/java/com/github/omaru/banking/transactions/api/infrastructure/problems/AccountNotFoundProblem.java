package com.github.omaru.banking.transactions.api.infrastructure.problems;

import com.github.omaru.banking.transactions.api.domain.exceptions.AccountNotFoundException;
import org.springframework.http.HttpStatus;

public class AccountNotFoundProblem extends DomainProblem {
  public AccountNotFoundProblem(AccountNotFoundException ex) {
    super(HttpStatus.NOT_FOUND.value(), "Account not found", ex.getMessage(), ex);
  }
}
