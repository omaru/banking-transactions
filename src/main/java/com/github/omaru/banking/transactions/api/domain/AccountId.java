package com.github.omaru.banking.transactions.api.domain;

import java.util.UUID;

public record AccountId(UUID id) {
    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }
}
