package com.github.omaru.banking.transactions.api.infrastructure.dtos;

import com.github.omaru.banking.transactions.api.application.ports.WithDrawMoneyUseCase;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record WithDrawRequest(@Positive double amount) {
  public WithDrawMoneyUseCase.WithDrawMoneyCommand toCommand(UUID accountId) {
    return new WithDrawMoneyUseCase.WithDrawMoneyCommand(accountId.toString(), amount);
  }
}
