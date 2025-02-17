package com.banking.backend.repository;

import com.banking.backend.model.database.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
    // Additional query methods can be defined here.

}
