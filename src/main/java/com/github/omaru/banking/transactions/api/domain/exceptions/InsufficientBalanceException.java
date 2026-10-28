/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain.exceptions;

public final class InsufficientBalanceException extends AccountException {
  public InsufficientBalanceException() {
    super("Insufficient balance");
  }
}
