# Banking Transactions

![Coverage](../.github/badges/jacoco.svg)

A simple project for practicing **hexagonal architecture** (Ports & Adapters) by building a banking transactions API with Spring Boot.

The goal is not the banking domain itself, but to exercise the separation between business logic and technical details (web, database, frameworks), so that the domain does not depend on anything external.

## Architecture

The code is organized into three layers under `com.github.omaru.banking.transactions.api`:

```
api
├── domain          # Pure domain model: entities, value objects and business rules
├── application     # Use cases and ports (inbound/outbound interfaces)
└── infrastructure  # Adapters: REST controllers, JPA persistence, configuration
```

- **domain**: knows nothing about Spring, JPA or HTTP. Business rules live here.
- **application**: orchestrates the use cases and defines the *ports* that the infrastructure must implement.
- **infrastructure**: implements the ports (inbound and outbound adapters) with concrete technologies.

Dependencies always point towards the domain: `infrastructure → application → domain`.

## Domain model

| Element           | Kind         | Description                                                                 |
|-------------------|--------------|-----------------------------------------------------------------------------|
| `Account`         | Entity       | Bank account; holds its balance and applies deposits and withdrawals.       |
| `AccountId`       | Value object | Account identifier, backed by a `UUID`.                                     |
| `Money`           | Value object | Immutable monetary amount over `BigDecimal` (add, subtract, negative check). |
| `Transaction`     | Entity       | A movement made on an account (type, amount, date).                         |
| `TransactionType` | Enum         | Transaction type: `DEPOSIT` or `WITHDRAW`.                                  |

## Development

Instructions for running the project locally are in [DEVELOPMENT.md](DEVELOPMENT.md).
