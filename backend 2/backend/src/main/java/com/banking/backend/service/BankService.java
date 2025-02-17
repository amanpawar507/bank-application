package com.banking.backend.service;

import com.banking.backend.model.api.bank.BankResponse;
import com.banking.backend.model.database.Bank;
import com.banking.backend.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    public BankResponse createBank(String name) {
        Bank bank = Bank.builder()
                .name(name)
                .build();
        Bank newBank = bankRepository.saveAndFlush(bank);

        return BankResponse.builder()
                .bankId((newBank.getBankId()))
                .name(newBank.getName())
                .build();
    }
}
