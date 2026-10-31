package com.github.omaru.banking.transactions.api.domain.exceptions;

public abstract class DomainException extends RuntimeException {
  public DomainException(String message) {
    super(message);
  }
}
