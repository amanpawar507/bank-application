package com.banking.backend.api;

import com.banking.backend.model.api.bank.BankResponse;
import com.banking.backend.model.api.bank.CreateBankRequest;
import com.banking.backend.service.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class BankControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BankService bankService;

    @Test
    void createBank() {
        CreateBankRequest bankRequest = CreateBankRequest.builder()
                .name("Test Bank")
                .build();

        webTestClient.post()
                .uri("/create-bank")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bankRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BankResponse.class)
                .value(bankResponse -> {
                    assertEquals("Test Bank", bankResponse.getName());
                });
    }
}
