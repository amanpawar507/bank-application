package com.banking.backend.repository;

import com.banking.backend.model.database.BankAccount;
import com.banking.backend.model.database.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    List<Transfer> findByFromAccountOrToAccount(BankAccount fromAccount, BankAccount toAccount);
}
