/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain;

import java.time.Instant;
import java.util.UUID;

public class Transaction {
    private final TransactionId id;

    private final TransactionType transactionType;
    private final Money amount;
    private final Instant createdAt;

    private Transaction(TransactionType transactionType, Money money) {
        this.id = TransactionId.newId();
        this.transactionType = transactionType;
        this.amount = money;
        this.createdAt = Instant.now();
    }

    public static Transaction deposit(Money amount) {
        return new Transaction(TransactionType.DEPOSIT, amount);
    }

    public static Transaction withdraw(Money amount) {
        return new Transaction(TransactionType.WITHDRAW, amount);
    }

    public UUID getId() {
        return id.id();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}
