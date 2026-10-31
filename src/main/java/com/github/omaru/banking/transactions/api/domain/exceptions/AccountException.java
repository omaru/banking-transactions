package com.github.omaru.banking.transactions.api.domain.exceptions;

public abstract sealed class AccountException extends DomainException
    permits InsufficientBalanceException, NegativeAmountException, AccountNotFoundException {

  public AccountException(String message) {
    super(message);
  }
}
