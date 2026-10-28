package com.github.omaru.banking.transactions.api.application.ports;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;

public interface DepositMoneyUseCase {
  record DepositMoneyCommand(String accountId, double amount) {}

  AccountDetailsDto execute(DepositMoneyCommand command);
}
