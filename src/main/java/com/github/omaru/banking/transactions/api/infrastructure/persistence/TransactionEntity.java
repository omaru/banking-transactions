package com.github.omaru.banking.transactions.api.infrastructure.persistence;

import com.github.omaru.banking.transactions.api.domain.Money;
import com.github.omaru.banking.transactions.api.domain.Transaction;
import com.github.omaru.banking.transactions.api.domain.TransactionId;
import com.github.omaru.banking.transactions.api.domain.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class TransactionEntity {
  @Id private String id;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private AccountEntity account;

  public Transaction toDomain() {
    return Transaction.from(
        TransactionId.from(this.id),
        TransactionType.from(this.type),
        Money.from(this.amount),
        this.createdAt);
  }

  public static TransactionEntity from(Transaction transaction) {
    var entity = new TransactionEntity();
    entity.setId(transaction.getId().toString());
    entity.setType(transaction.getTransactionType().name());
    entity.setAmount(transaction.getAmount().getAmount());
    entity.setCreatedAt(transaction.getCreatedAt());
    return entity;
  }
}
