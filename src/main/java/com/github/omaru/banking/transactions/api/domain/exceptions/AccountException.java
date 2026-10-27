/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain.exceptions;

public abstract sealed class AccountException extends RuntimeException
        permits InsufficientBalanceException, NegativeAmountException,AccountNotFoundException {

    public AccountException(String message) {
        super(message);
    }

}
