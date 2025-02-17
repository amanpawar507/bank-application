package com.banking.backend.api;

import com.banking.backend.model.api.customer.CreateCustomerRequest;
import com.banking.backend.model.api.customer.CustomerResponse;
import com.banking.backend.model.database.Customer;
import com.banking.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create-customer")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        CustomerResponse customerResponse = customerService.createCustomer(request);
        return ResponseEntity.ok(customerResponse);
    }

}
