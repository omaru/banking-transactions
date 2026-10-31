package com.github.omaru.banking.transactions.api.infrastructure.problems;

public abstract class DomainProblem extends ApiProblem {

  public DomainProblem(int status, String title, String detail, RuntimeException ex) {
    super(status, title, detail, ex);
  }
}
