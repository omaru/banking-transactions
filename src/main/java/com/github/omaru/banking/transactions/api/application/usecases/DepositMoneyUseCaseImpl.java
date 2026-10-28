package com.github.omaru.banking.transactions.api.application.usecases;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;
import com.github.omaru.banking.transactions.api.application.ports.DepositMoneyUseCase;
import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.Money;
import com.github.omaru.banking.transactions.api.domain.exceptions.AccountNotFoundException;
import com.github.omaru.banking.transactions.api.domain.ports.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositMoneyUseCaseImpl implements DepositMoneyUseCase {
  private final AccountRepository accountRepository;

  @Override
  @Transactional
  public AccountDetailsDto execute(DepositMoneyCommand command) {
    var accountId = AccountId.from(command.accountId());
    var account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    account.deposit(Money.of(command.amount()));
    accountRepository.save(account);
    return AccountDetailsDto.from(account);
  }
}
