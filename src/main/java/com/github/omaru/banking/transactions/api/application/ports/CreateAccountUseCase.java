package com.github.omaru.banking.transactions.api.application.ports;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;
import java.math.BigDecimal;

public interface CreateAccountUseCase {
  record CreateAccountCommand(String customerId, BigDecimal initialBalance) {}

  AccountDetailsDto createAccount(CreateAccountCommand command);
}
