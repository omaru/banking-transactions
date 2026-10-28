package com.github.omaru.banking.transactions.api.infrastructure.dtos;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record AccountResponse(
    String id, String customerId, BigDecimal balance, List<TransactionSummary> transactions) {
  private static final int BALANCE_SCALE = 2;

  public static AccountResponse from(AccountDetailsDto accountDetails) {
    List<TransactionSummary> transactions =
        accountDetails.transactions().stream().map(TransactionSummary::from).toList();
    return new AccountResponse(
        accountDetails.id(),
        accountDetails.customerId(),
        accountDetails.balance().setScale(BALANCE_SCALE, RoundingMode.HALF_EVEN),
        transactions);
  }
}
