package com.github.omaru.banking.transactions.api.domain;

public enum TransactionType {
  DEPOSIT,
  WITHDRAW;

  public static TransactionType from(String type) {
    return TransactionType.valueOf(type.toUpperCase());
  }
}
