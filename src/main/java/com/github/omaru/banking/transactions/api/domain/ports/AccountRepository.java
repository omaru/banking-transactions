package com.github.omaru.banking.transactions.api.domain.ports;

import com.github.omaru.banking.transactions.api.domain.Account;
import com.github.omaru.banking.transactions.api.domain.AccountId;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(AccountId id);
}
