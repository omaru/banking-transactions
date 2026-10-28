package com.github.omaru.banking.transactions.api.infrastructure.controllers;

import static org.assertj.core.api.BDDAssertions.then;

import com.github.omaru.banking.transactions.api.infrastructure.dtos.AccountResponse;
import com.github.omaru.banking.transactions.api.test.ComponentTest;
import java.math.BigDecimal;
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
}
