package com.github.omaru.banking.transactions.api.domain;

import java.util.UUID;

public record AccountId(UUID id) {
  public static AccountId newId() {
    return new AccountId(UUID.randomUUID());
  }

  public static AccountId from(String id) {
    return new AccountId(UUID.fromString(id));
  }

  public static AccountId from(UUID id) {
    return new AccountId(id);
  }
}
