package com.github.omaru.banking.transactions.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.omaru.banking.transactions.api.domain.Account;
import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.CustomerId;
import com.github.omaru.banking.transactions.api.domain.Money;
import com.github.omaru.banking.transactions.api.domain.TransactionType;
import com.github.omaru.banking.transactions.api.domain.exceptions.InsufficientBalanceException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccountTest {

  @Test
  void shouldDepositMoneyCorrectly() {
    Account account =
        new Account(
            AccountId.newId(),
            CustomerId.from(UUID.fromString("de96244a-c19a-445b-8ce1-7583776ac8b6")),
            Money.of(100));

    account.deposit(Money.of(50));

    assertThat(account.getBalance()).isEqualTo(Money.of(150));
    assertThat(account.getTransactions().getFirst().getTransactionType())
        .isEqualTo(TransactionType.DEPOSIT);
    assertThat(account.getTransactions()).hasSize(1);
  }

  @Test
  void shouldWithdrawMoneyCorrectly() {
    Account account =
        new Account(
            AccountId.newId(),
            CustomerId.from(UUID.fromString("de96244a-c19a-445b-8ce1-7583776ac8b6")),
            Money.of(100));

    account.withdraw(Money.of(30));

    assertThat(account.getBalance()).isEqualTo(Money.of(70));
    assertThat(account.getTransactions().getFirst().getTransactionType())
        .isEqualTo(TransactionType.WITHDRAW);
    assertThat(account.getTransactions()).hasSize(1);
  }

  @Test
  void shouldNotAllowWithdrawWithInsufficientBalance() {
    Account account =
        new Account(
            AccountId.newId(),
            CustomerId.from(UUID.fromString("de96244a-c19a-445b-8ce1-7583776ac8b6")),
            Money.of(100));

    Exception ex =
        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(Money.of(150)));

    assertThat(ex).hasMessageContaining("Insufficient balance");
  }
}
