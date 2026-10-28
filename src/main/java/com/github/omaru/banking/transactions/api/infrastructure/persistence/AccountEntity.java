package com.github.omaru.banking.transactions.api.infrastructure.persistence;

import com.github.omaru.banking.transactions.api.domain.Account;
import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.CustomerId;
import com.github.omaru.banking.transactions.api.domain.Money;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
public class AccountEntity {
  @Id private String id;

  @Column(nullable = false)
  private String customerId;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal balance;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TransactionEntity> transactions = new ArrayList<>();

  public void addTransaction(TransactionEntity transaction) {
    transaction.setAccount(this);
    this.transactions.add(transaction);
  }

  public Account toDomain() {
    var account =
        new Account(
            AccountId.from(this.id), CustomerId.from(this.customerId), Money.from(this.balance));
    account.setTransactions(
        this.getTransactions().stream().map(TransactionEntity::toDomain).toList());
    return account;
  }

  public static AccountEntity from(Account account) {
    var entity = new AccountEntity();
    entity.setId(account.getId().id().toString());
    entity.setCustomerId(account.getCustomerId().id().toString());
    entity.setBalance(account.getBalance().getAmount());
    entity.getTransactions().clear();
    account.getTransactions().stream().map(TransactionEntity::from).forEach(entity::addTransaction);
    return entity;
  }
}
