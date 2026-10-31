package com.github.omaru.banking.transactions.api.infrastructure.problems;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.github.omaru.banking.transactions.api.domain.AccountId;
import com.github.omaru.banking.transactions.api.domain.exceptions.AccountNotFoundException;
import com.github.omaru.banking.transactions.api.domain.exceptions.DomainException;
import com.github.omaru.banking.transactions.api.domain.exceptions.InsufficientBalanceException;
import com.github.omaru.banking.transactions.api.domain.exceptions.NegativeAmountException;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class DomainProblemMapperTest {
  private final DomainProblemMapper mapper = new DomainProblemMapper();

  @Test
  void shouldMapAccountNotFound() {
    var ex = new AccountNotFoundException(AccountId.from(UUID.randomUUID().toString()));

    DomainProblem problem = mapper.toProblem(ex);

    then(problem).isInstanceOf(AccountNotFoundProblem.class);
    then(problem.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    then(problem.getDetail()).isEqualTo(ex.getMessage());
    then(problem.getType()).isEqualTo(URI.create("urn:omaru:error:account-not-found"));
  }

  @Test
  void shouldMapInsufficientBalance() {
    DomainProblem problem = mapper.toProblem(new InsufficientBalanceException());

    then(problem).isInstanceOf(InsufficientBalanceProblem.class);
    then(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(problem.getType()).isEqualTo(URI.create("urn:omaru:error:insufficient-balance"));
  }

  @Test
  void shouldMapNegativeAmount() {
    DomainProblem problem = mapper.toProblem(new NegativeAmountException("Amount is negative"));

    then(problem).isInstanceOf(NegativeAmountProblem.class);
    then(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    then(problem.getDetail()).isEqualTo("Amount is negative");
    then(problem.getType()).isEqualTo(URI.create("urn:omaru:error:negative-amount"));
  }

  @Test
  void shouldMapUnknownDomainExceptionToDefaultProblem() {
    DomainException ex = mock(DomainException.class);
    given(ex.getMessage()).willReturn("Something failed");

    DomainProblem problem = mapper.toProblem(ex);

    then(problem).isInstanceOf(DefaultDomainProblem.class);
    then(problem.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    then(problem.getDetail()).isEqualTo("Something failed");
  }
}
