package com.github.omaru.banking.transactions.api.application.ports;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;

public interface GetAccountDetailsUseCase {
  record GetAccountCommand(String accountId) {}

  AccountDetailsDto getById(GetAccountCommand command);
}
