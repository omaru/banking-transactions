package com.github.omaru.banking.transactions.api.infrastructure.problems;

import com.github.omaru.banking.transactions.api.domain.exceptions.AccountNotFoundException;
import com.github.omaru.banking.transactions.api.domain.exceptions.DomainException;
import com.github.omaru.banking.transactions.api.domain.exceptions.InsufficientBalanceException;
import com.github.omaru.banking.transactions.api.domain.exceptions.NegativeAmountException;

public class DomainProblemMapper {

  public DomainProblem toProblem(DomainException ex) {
    return switch (ex) {
      case AccountNotFoundException e -> new AccountNotFoundProblem(e);
      case InsufficientBalanceException e -> new InsufficientBalanceProblem(e);
      case NegativeAmountException e -> new NegativeAmountProblem(e);
      default -> new DefaultDomainProblem(ex.getMessage(), ex);
    };
  }
}
