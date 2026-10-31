package com.github.omaru.banking.transactions.api.infrastructure.controllers;

import com.github.omaru.banking.transactions.api.application.ports.CreateAccountUseCase;
import com.github.omaru.banking.transactions.api.application.ports.DepositMoneyUseCase;
import com.github.omaru.banking.transactions.api.application.ports.GetAccountDetailsUseCase;
import com.github.omaru.banking.transactions.api.application.ports.WithDrawMoneyUseCase;
import com.github.omaru.banking.transactions.api.infrastructure.dtos.AccountResponse;
import com.github.omaru.banking.transactions.api.infrastructure.dtos.CreateAccountRequest;
import com.github.omaru.banking.transactions.api.infrastructure.dtos.DepositRequest;
import com.github.omaru.banking.transactions.api.infrastructure.dtos.WithDrawRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Bank account management")
public class AccountController {
  private final CreateAccountUseCase createAccountUseCase;
  private final DepositMoneyUseCase depositMoneyUseCase;
  private final WithDrawMoneyUseCase withDrawMoneyUseCase;
  private final GetAccountDetailsUseCase getAccountDetailsUseCase;

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
        AccountResponse.from(createAccountUseCase.execute(request.toCommand()));
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Deposit money",
      description =
          "Deposits a positive amount into an existing account"
              + " and returns the updated account details.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Deposit successfully applied",
        content = @Content(schema = @Schema(implementation = AccountResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request: malformed accountId or non-positive amount",
        content = @Content)
  })
  @PostMapping(path = "/{accountId}")
  public ResponseEntity<AccountResponse> deposit(
      @Parameter(description = "Unique identifier of the account", required = true) @PathVariable
          UUID accountId,
      @Valid @RequestBody DepositRequest request) {
    AccountResponse response =
        AccountResponse.from(depositMoneyUseCase.execute(request.toCommand(accountId)));
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Withdraw money",
      description =
          "Withdraws a positive amount from an existing account"
              + " and returns the updated account details.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Withdrawal successfully applied",
        content = @Content(schema = @Schema(implementation = AccountResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request: malformed accountId or non-positive amount",
        content = @Content)
  })
  @PostMapping(path = "/{accountId}/withdraw")
  public ResponseEntity<AccountResponse> withdraw(
      @Parameter(description = "Unique identifier of the account", required = true) @PathVariable
          UUID accountId,
      @Valid @RequestBody WithDrawRequest request) {
    AccountResponse response =
        AccountResponse.from(withDrawMoneyUseCase.execute(request.toCommand(accountId)));
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Get account details",
      description = "Returns the details of an existing account, including its current balance.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Account details found",
        content = @Content(schema = @Schema(implementation = AccountResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request: malformed accountId",
        content = @Content)
  })
  @GetMapping(path = "/{accountId}")
  public ResponseEntity<AccountResponse> getAccountDetails(
      @Parameter(description = "Unique identifier of the account", required = true) @PathVariable
          UUID accountId) {
    AccountResponse response =
        AccountResponse.from(
            getAccountDetailsUseCase.execute(
                new GetAccountDetailsUseCase.GetAccountCommand(accountId.toString())));
    return ResponseEntity.ok(response);
  }
}
