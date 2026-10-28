package com.github.omaru.banking.transactions.api.infrastructure.repositories;

import com.github.omaru.banking.transactions.api.infrastructure.persistence.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, String> {}
