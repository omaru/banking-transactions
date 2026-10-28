package com.github.omaru.banking.transactions.api;

import com.github.omaru.banking.transactions.api.test.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfig.class)
class BankingTransactionsApiApplicationTests {

  @Test
  void contextLoads() {}
}
