package com.github.omaru.banking.transactions.api.domain.exceptions;

import com.github.omaru.banking.transactions.api.domain.AccountId;

public final class AccountNotFoundException extends AccountException {
  public AccountNotFoundException(AccountId accountId) {
    super("Account not found: " + accountId);
  }
}
