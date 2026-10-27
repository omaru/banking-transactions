/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain;

import com.github.omaru.banking.transactions.api.domain.exceptions.InsufficientBalanceException;
import com.github.omaru.banking.transactions.api.domain.exceptions.NegativeAmountException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Account {
    private final AccountId id;
    private final CustomerId customerId;
    private Money balance;
    private List<Transaction> transactions = new ArrayList<>();


    public Account(AccountId id, CustomerId customerId) {
        this.id = id;
        this.customerId = customerId;
        this.balance = Money.zero();
    }

    public Account(AccountId id, CustomerId customerId, Money initialBalance) {
        this(id, customerId);
        this.balance = initialBalance;
    }

    public void deposit(Money amount) {
        requireNonNull(amount, "Deposit amount cannot be null");
        if (amount.compareTo(Money.zero()) <= 0) {
            throw new NegativeAmountException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
        this.transactions.add(Transaction.deposit(amount));
    }

    public void withdraw(Money amount) {
        requireNonNull(amount, "Withdrawal amount cannot be null");
        if (amount.compareTo(Money.zero()) <= 0) {
            throw new NegativeAmountException("Withdrawal amount must be positive");
        }
        Money newBalance = this.balance.subtract(amount);
        if (newBalance.isNegative()) {
            throw new InsufficientBalanceException();
        }
        this.balance = newBalance;
        this.transactions.add(Transaction.withdraw(amount));

    }

    public AccountId getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
