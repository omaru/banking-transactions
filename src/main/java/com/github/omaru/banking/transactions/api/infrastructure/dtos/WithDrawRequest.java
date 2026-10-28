package com.github.omaru.banking.transactions.api.infrastructure.dtos;

import jakarta.validation.constraints.Positive;

public record WithDrawRequest(@Positive double amount) {}
