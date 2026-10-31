package com.github.omaru.banking.transactions.api.infrastructure.problems;

import java.net.URI;
import org.springframework.http.ProblemDetail;

public abstract class ApiProblem extends ProblemDetail {
  public ApiProblem(int status, String title, String detail, RuntimeException ex) {
    super(status);
    setTitle(title);
    setDetail(detail);
    String errorName = toErrorName(ex);
    setType(URI.create("urn:omaru:error:" + errorName));
  }

  private String toErrorName(RuntimeException ex) {
    return ex.getClass()
        .getSimpleName()
        .replace("Exception", "")
        .replaceAll("([a-z])([A-Z])", "$1-$2")
        .toLowerCase();
  }
}
