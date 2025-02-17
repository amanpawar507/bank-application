package com.banking.backend.api;

import com.banking.backend.model.api.customer.CreateCustomerRequest;
import com.banking.backend.model.api.customer.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createCustomer() {
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        webTestClient.post()
                .uri("/create-customer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class)
                .value(response -> {
                    assertEquals("John", response.getFirstName());
                    assertEquals("Doe", response.getLastName());
                    assertEquals("johndoe@gmail.com", response.getEmail());
                });
    }
}

