package com.github.omaru.banking.transactions.api.infrastructure.controllers;

import com.github.omaru.banking.transactions.api.application.ports.CreateAccountUseCase;
import com.github.omaru.banking.transactions.api.infrastructure.dtos.AccountResponse;
import com.github.omaru.banking.transactions.api.infrastructure.dtos.CreateAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Bank account management")
public class AccountController {
  private final CreateAccountUseCase createAccountUseCase;

  @Operation(
      summary = "Create an account",
      description =
          "Creates a new bank account for a customer with a positive initial balance"
              + " and returns the details of the created account.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Account successfully created",
        content = @Content(schema = @Schema(implementation = AccountResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request: empty customerId or non-positive initialBalance",
        content = @Content)
  })
  @PostMapping
  public ResponseEntity<AccountResponse> createAccount(
      @Valid @RequestBody CreateAccountRequest request) {
    AccountResponse response =
        AccountResponse.from(createAccountUseCase.createAccount(request.toCommand()));
    return ResponseEntity.ok(response);
  }
}
