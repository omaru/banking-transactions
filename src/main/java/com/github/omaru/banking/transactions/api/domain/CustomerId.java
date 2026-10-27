/* (C)2026 */
package com.github.omaru.banking.transactions.api.domain;

import java.util.UUID;

public record CustomerId(UUID id) {
    private CustomerId() {
        this(UUID.randomUUID());
    }

    public static CustomerId newId() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId from(UUID id) {
        return new CustomerId(id);
    }

    public static CustomerId from(String id) {
        return CustomerId.from(UUID.fromString(id));
    }
}
