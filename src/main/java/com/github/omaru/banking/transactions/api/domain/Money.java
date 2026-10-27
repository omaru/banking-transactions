/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Money {
    private final BigDecimal amount;
    private static final Money ZERO = new Money(BigDecimal.ZERO);


    private Money(BigDecimal amount) {
        requireNonNull(amount, "Amount must have a value");
        this.amount = amount;
    }

    public static Money of(double value) {
        return Money.from(BigDecimal.valueOf(value));
    }

    public static Money from(BigDecimal value) {
        return new Money(value);
    }

    public static Money zero() {
        return ZERO;
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public boolean isNegative() {
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int compareTo(Money other) {
        return this.amount.compareTo(other.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

}
