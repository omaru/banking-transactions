package com.github.omaru.banking.transactions.api.infrastructure.problems;

import com.github.omaru.banking.transactions.api.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final DomainProblemMapper problemMapper;

  @ExceptionHandler(DomainException.class)
  public ApiProblem accountNotFound(DomainException ex) {
    return problemMapper.toProblem(ex);
  }
}
