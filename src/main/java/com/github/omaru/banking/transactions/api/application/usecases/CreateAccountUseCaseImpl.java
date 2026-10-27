package com.github.omaru.banking.transactions.api.application.usecases;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;
import com.github.omaru.banking.transactions.api.application.ports.CreateAccountUseCase;
import com.github.omaru.banking.transactions.api.domain.Account;
import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.CustomerId;
import com.github.omaru.banking.transactions.api.domain.ports.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {
    private final AccountRepository accountRepository;


    @Override
    @Transactional
    public AccountDetailsDto createAccount(CreateAccountUseCase.CreateAccountCommand command) {
        var account = new Account(
                AccountId.newId(),
                CustomerId.from(command.customerId())
        );
        Account saved = accountRepository.save(account);
        return AccountDetailsDto.from(saved);
    }
}
