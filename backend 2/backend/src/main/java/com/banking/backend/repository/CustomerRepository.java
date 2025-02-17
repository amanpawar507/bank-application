package com.banking.backend.repository;

import com.banking.backend.model.database.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    // You can add custom query methods if needed.

}
