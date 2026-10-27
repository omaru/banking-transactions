package com.github.omaru.banking.transactions.api.application.ports;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;

public interface WithDrawMoneyUseCase {
    record WithDrawMoneyCommand(
            String accountId,
            double amount
    ) {
    }

    AccountDetailsDto withdrawMoney(WithDrawMoneyCommand command);
}
