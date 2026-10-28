package com.github.omaru.banking.transactions.api.infrastructure.dtos;

import com.github.omaru.banking.transactions.api.application.ports.DepositMoneyUseCase;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record DepositRequest(@Positive double amount) {
  public DepositMoneyUseCase.DepositMoneyCommand toCommand(UUID accountId) {
    return new DepositMoneyUseCase.DepositMoneyCommand(accountId.toString(), amount);
  }
}
