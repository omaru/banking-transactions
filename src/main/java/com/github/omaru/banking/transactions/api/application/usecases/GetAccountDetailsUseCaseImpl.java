package com.github.omaru.banking.transactions.api.application.usecases;

import com.github.omaru.banking.transactions.api.application.dtos.AccountDetailsDto;
import com.github.omaru.banking.transactions.api.application.ports.GetAccountDetailsUseCase;
import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.exceptions.AccountNotFoundException;
import com.github.omaru.banking.transactions.api.domain.ports.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAccountDetailsUseCaseImpl implements GetAccountDetailsUseCase {
  private final AccountRepository accountRepository;

  @Override
  public AccountDetailsDto execute(GetAccountCommand command) {
    var accountId = AccountId.from(command.accountId());
    var account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    return AccountDetailsDto.from(account);
  }
}
