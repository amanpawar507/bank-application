package com.banking.backend.repository;

import com.banking.backend.model.database.Bank;
import com.banking.backend.model.database.BankAccount;
import com.banking.backend.model.database.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    List<BankAccount> findByCustomerAndBank(Customer customer, Bank bank);

}
