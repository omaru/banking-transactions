package com.github.omaru.banking.transactions.api.infrastructure.dtos;

import com.github.omaru.banking.transactions.api.application.dtos.TransactionDto;

public record TransactionSummary(String id, String type, double amount) {
  public static TransactionSummary from(TransactionDto transactionDto) {
    return new TransactionSummary(
        transactionDto.id(), transactionDto.type(), transactionDto.amount());
  }
}
