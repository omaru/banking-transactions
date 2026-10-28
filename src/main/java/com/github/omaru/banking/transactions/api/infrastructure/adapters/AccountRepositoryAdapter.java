package com.github.omaru.banking.transactions.api.infrastructure.adapters;

import com.github.omaru.banking.transactions.api.domain.Account;
import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.ports.AccountRepository;
import com.github.omaru.banking.transactions.api.infrastructure.persistence.AccountEntity;
import com.github.omaru.banking.transactions.api.infrastructure.repositories.SpringDataAccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {
  private final SpringDataAccountRepository repository;

  @Override
  public Account save(Account account) {
    return repository.save(AccountEntity.from(account)).toDomain();
  }

  @Override
  public Optional<Account> findById(AccountId id) {
    return repository.findById(id.id().toString()).map(AccountEntity::toDomain);
  }
}
