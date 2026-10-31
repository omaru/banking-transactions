package com.github.omaru.banking.transactions.api.infrastructure.controllers;

import static org.assertj.core.api.BDDAssertions.then;

import com.github.omaru.banking.transactions.api.infrastructure.dtos.AccountResponse;
import com.github.omaru.banking.transactions.api.test.ComponentTest;
import com.jayway.jsonpath.JsonPath;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

@ComponentTest
class AccountControllerCT {
  private static final String CUSTOMER_ID = "868805bd-c9d1-4521-b5b0-ce31e47571a2";

  @Autowired private MockMvcTester mvc;

  @Test
  void shouldCreateAccountWithInitialBalanceRoundedToTwoDecimals() {
    String request =
        """
                        {
                          "customerId": "%s",
                          "initialBalance": 1.1
                        }
                        """
            .formatted(CUSTOMER_ID);

    MvcTestResult result =
        mvc.post()
            .uri("/v1/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
            .exchange();

    then(result).hasStatus(HttpStatus.OK);
    then(result).bodyText().contains("\"balance\":1.10");
    then(result)
        .bodyJson()
        .convertTo(AccountResponse.class)
        .satisfies(
            account -> {
              then(account.id()).isNotBlank();
              then(account.customerId()).isEqualTo(CUSTOMER_ID);
              then(account.balance()).isEqualTo(new BigDecimal("1.10"));
              then(account.transactions()).isEmpty();
            });
  }

  @Test
  void shouldDepositMoneyIntoAccount() throws Exception {
    String accountId = createAccount(10);

    MvcTestResult result = post("/v1/accounts/" + accountId, amountRequest(5.5));

    then(result).hasStatus(HttpStatus.OK);
    then(result)
        .bodyJson()
        .convertTo(AccountResponse.class)
        .satisfies(
            account -> {
              then(account.id()).isEqualTo(accountId);
              then(account.balance()).isEqualTo(new BigDecimal("15.50"));
              then(account.transactions()).hasSize(1);
              then(account.transactions().getFirst().type()).isEqualTo("DEPOSIT");
              then(account.transactions().getFirst().amount()).isEqualTo(5.5);
            });
  }

  @Test
  void shouldWithdrawMoneyFromAccount() throws Exception {
    String accountId = createAccount(10);

    MvcTestResult result = post("/v1/accounts/" + accountId + "/withdraw", amountRequest(4));

    then(result).hasStatus(HttpStatus.OK);
    then(result)
        .bodyJson()
        .convertTo(AccountResponse.class)
        .satisfies(
            account -> {
              then(account.id()).isEqualTo(accountId);
              then(account.balance()).isEqualTo(new BigDecimal("6.00"));
              then(account.transactions()).hasSize(1);
              then(account.transactions().getFirst().type()).isEqualTo("WITHDRAW");
              then(account.transactions().getFirst().amount()).isEqualTo(4.0);
            });
  }

  @Test
  void shouldGetAccountDetailsWithPersistedTransactions() throws Exception {
    String accountId = createAccount(10);
    post("/v1/accounts/" + accountId, amountRequest(5));

    MvcTestResult result = mvc.get().uri("/v1/accounts/" + accountId).exchange();

    then(result).hasStatus(HttpStatus.OK);
    then(result)
        .bodyJson()
        .convertTo(AccountResponse.class)
        .satisfies(
            account -> {
              then(account.id()).isEqualTo(accountId);
              then(account.customerId()).isEqualTo(CUSTOMER_ID);
              then(account.balance()).isEqualTo(new BigDecimal("15.00"));
              then(account.transactions()).hasSize(1);
            });
  }

  @Test
  void shouldRejectWithdrawalAboveBalance() throws Exception {
    String accountId = createAccount(10);

    MvcTestResult result = post("/v1/accounts/" + accountId + "/withdraw", amountRequest(10.01));

    then(result).hasStatus(HttpStatus.BAD_REQUEST);
    then(result)
        .bodyJson()
        .extractingPath("$.type")
        .isEqualTo("urn:omaru:error:insufficient-balance");
  }

  @Test
  void shouldReturnNotFoundWhenGettingUnknownAccount() {
    MvcTestResult result = mvc.get().uri("/v1/accounts/" + UUID.randomUUID()).exchange();

    then(result).hasStatus(HttpStatus.NOT_FOUND);
    then(result).bodyJson().extractingPath("$.type").isEqualTo("urn:omaru:error:account-not-found");
  }

  @Test
  void shouldReturnNotFoundWhenDepositingIntoUnknownAccount() {
    MvcTestResult result = post("/v1/accounts/" + UUID.randomUUID(), amountRequest(1));

    then(result).hasStatus(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldReturnNotFoundWhenWithdrawingFromUnknownAccount() {
    MvcTestResult result =
        post("/v1/accounts/" + UUID.randomUUID() + "/withdraw", amountRequest(1));

    then(result).hasStatus(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldRejectNonPositiveDeposit() throws Exception {
    String accountId = createAccount(10);

    MvcTestResult result = post("/v1/accounts/" + accountId, amountRequest(-1));

    then(result).hasStatus(HttpStatus.BAD_REQUEST);
  }

  private String createAccount(double initialBalance) throws Exception {
    String request =
        """
        {
          "customerId": "%s",
          "initialBalance": %s
        }
        """
            .formatted(CUSTOMER_ID, initialBalance);
    MvcTestResult result = post("/v1/accounts", request);
    then(result).hasStatus(HttpStatus.OK);
    return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
  }

  private MvcTestResult post(String uri, String request) {
    return mvc.post().uri(uri).contentType(MediaType.APPLICATION_JSON).content(request).exchange();
  }

  private String amountRequest(double amount) {
    return """
        {
          "amount": %s
        }
        """
        .formatted(amount);
  }
}
