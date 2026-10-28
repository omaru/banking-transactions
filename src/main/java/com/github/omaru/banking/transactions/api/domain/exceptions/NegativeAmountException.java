/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain.exceptions;

public final class NegativeAmountException extends AccountException {
  public NegativeAmountException(String message) {
    super(message);
  }
}
