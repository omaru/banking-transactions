package com.github.omaru.banking.transactions.api.application.dtos;

import com.github.omaru.banking.transactions.api.domain.Transaction;

public record TransactionDto(String id, String type, double amount) {
  public static TransactionDto from(Transaction transaction) {
    return new TransactionDto(
        transaction.getId().toString(),
        transaction.getTransactionType().name(),
        transaction.getAmount().getAmount().doubleValue());
  }
}
