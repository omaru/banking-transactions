package com.github.omaru.banking.transactions.api.infrastructure.dtos;

import com.github.omaru.banking.transactions.api.application.ports.CreateAccountUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateAccountRequest(
    @NotBlank String customerId, @NotNull @Positive BigDecimal initialBalance) {
  public CreateAccountUseCase.CreateAccountCommand toCommand() {
    return new CreateAccountUseCase.CreateAccountCommand(customerId, initialBalance);
  }
}
