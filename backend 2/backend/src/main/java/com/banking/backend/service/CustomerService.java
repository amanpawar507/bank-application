package com.banking.backend.service;

import com.banking.backend.model.api.customer.CreateCustomerRequest;
import com.banking.backend.model.api.customer.CustomerResponse;
import com.banking.backend.model.database.Customer;
import com.banking.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        Customer newCustomer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .customerId((newCustomer.getCustomerId()))
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .email(newCustomer.getEmail())
                .createdAt(newCustomer.getCreatedAt())
                .build();
    }
}
