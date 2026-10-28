/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain;

import java.util.UUID;

public record TransactionId(UUID id) {
  public static TransactionId newId() {
    return new TransactionId(UUID.randomUUID());
  }

  public static TransactionId from(String id) {
    return new TransactionId(UUID.fromString(id));
  }
}
