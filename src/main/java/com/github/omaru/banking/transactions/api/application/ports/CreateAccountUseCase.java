package com.github.omaru.banking.transactions.api.application.ports;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;

public interface CreateAccountUseCase {
    record CreateAccountCommand(
            String customerId,
            double initialBalance
    ) {
    }
    AccountDetailsDto createAccount(CreateAccountCommand command);
}
