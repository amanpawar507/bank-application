package com.banking.backend.api;

import com.banking.backend.model.api.account.CreateAccountRequest;
import com.banking.backend.model.api.account.CreateAccountResponse;
import com.banking.backend.model.api.balance.BalanceResponse;
import com.banking.backend.model.api.bank.BankResponse;
import com.banking.backend.model.api.bank.CreateBankRequest;
import com.banking.backend.model.api.customer.CreateCustomerRequest;
import com.banking.backend.model.api.customer.CustomerResponse;
import com.banking.backend.model.api.transfer.TransferRequest;
import com.banking.backend.model.api.transfer.TransferResponse;
import com.banking.backend.model.database.Bank;
import com.banking.backend.service.BankAccountService;
import com.banking.backend.service.BankService;
import com.banking.backend.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BankAccountControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CustomerService customerService;

    private static final CreateCustomerRequest customerRequest = CreateCustomerRequest.builder()
            .firstName("John")
            .lastName("Doe")
            .email("johndoe@gmail.com")
            .build();

    @Test
    void createBankAccount() {
        BankResponse bankResponse = bankService.createBank("Test Bank");
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);

        CreateAccountRequest request = CreateAccountRequest.builder()
                .customerId(customerResponse.getCustomerId())
                .initialDeposit(BigDecimal.valueOf(1000))
                .bankId(bankResponse.getBankId())
                .build();

        webTestClient.post()
                .uri("/bank-accounts/create-account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateAccountResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(customerRequest.getFirstName(), response.getFirstName());
                });
    }

    @Test
    void getBalance(){
        BankResponse bankResponse = bankService.createBank("Test Bank");
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);

        CreateAccountRequest request = CreateAccountRequest.builder()
                .customerId(customerResponse.getCustomerId())
                .initialDeposit(BigDecimal.valueOf(1000))
                .bankId(bankResponse.getBankId())
                .build();
        CreateAccountResponse createAccountResponse = bankAccountService.createAccount(request);

        webTestClient.get()
                .uri("/bank-accounts/balance/{accountId}", createAccountResponse.getAccountId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BalanceResponse.class)
                .value(response -> {
                    assertEquals(createAccountResponse.getAccountId(), response.getAccountId());
                    assertNotNull(response.getBalance());
                });
    }

    @Test
    void transferFundAndHistory() {
        BankResponse bankResponse = bankService.createBank("Test Bank");
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);

        CreateAccountRequest request = CreateAccountRequest.builder()
                .customerId(customerResponse.getCustomerId())
                .initialDeposit(BigDecimal.valueOf(1000))
                .bankId(bankResponse.getBankId())
                .build();
        CreateAccountResponse createAccountResponse1 = bankAccountService.createAccount(request);
        CreateAccountResponse createAccountResponse2 = bankAccountService.createAccount(request);

        TransferRequest transferRequest = TransferRequest.builder()
                .fromAccountId(createAccountResponse1.getAccountId())
                .toAccountId(createAccountResponse2.getAccountId())
                .amount(BigDecimal.valueOf(100))
                .build();

        webTestClient.post()
                .uri("/bank-accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transferRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TransferResponse.class)
                .value(response -> {
                    assertEquals(transferRequest.getFromAccountId(), response.getFromAccountId());
                    assertEquals(transferRequest.getToAccountId(), response.getToAccountId());
                    assertEquals(transferRequest.getAmount(), response.getAmount());
                });

        webTestClient.get()
                .uri("/bank-accounts/transfer-history/{accountId}", createAccountResponse1.getAccountId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(TransferResponse.class)
                .value(Assertions::assertNotNull);
    }
}
