package com.github.omaru.banking.transactions.api.application.dtos;

import com.github.omaru.banking.transactions.api.domain.Account;
import java.math.BigDecimal;
import java.util.List;

public record AccountDetailsDto(
    String id, String customerId, BigDecimal balance, List<TransactionDto> transactions) {
  public static AccountDetailsDto from(Account account) {
    List<TransactionDto> transactions =
        account.getTransactions().stream().map(TransactionDto::from).toList();
    return new AccountDetailsDto(
        account.getId().id().toString(),
        account.getCustomerId().id().toString(),
        account.getBalance().getAmount(),
        transactions);
  }
}
