package com.banking.backend.service;

import com.banking.backend.exception.NotFoundException;
import com.banking.backend.exception.UnprocessableContentException;
import com.banking.backend.model.api.account.CreateAccountRequest;
import com.banking.backend.model.api.account.CreateAccountResponse;
import com.banking.backend.model.api.balance.BalanceResponse;
import com.banking.backend.model.api.transfer.TransferRequest;
import com.banking.backend.model.api.transfer.TransferResponse;
import com.banking.backend.model.database.Bank;
import com.banking.backend.model.database.BankAccount;
import com.banking.backend.model.database.Customer;
import com.banking.backend.model.database.Transfer;
import com.banking.backend.repository.BankAccountRepository;
import com.banking.backend.repository.BankRepository;
import com.banking.backend.repository.CustomerRepository;
import com.banking.backend.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final CustomerRepository customerRepository;

    private final TransferRepository transferRepository;

    private final BankRepository bankRepository;

    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found for customer id: " + request.getCustomerId() ));
        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() -> new NotFoundException("Bank not found for bank id: " + request.getBankId()));

        BankAccount account = BankAccount.builder()
                .accountNumber(Math.abs(new Random().nextLong()))
                .balance(request.getInitialDeposit())
                .customer(customer)
                .bank(bank)
                .build();

        BankAccount bankAccount = bankAccountRepository.saveAndFlush(account);

        return CreateAccountResponse.builder()
                .accountId(bankAccount.getAccountId())
                .accountNumber(String.valueOf(bankAccount.getAccountNumber()))
                .balance(bankAccount.getBalance())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .bankName(bank.getName())
                .build();
    }

    @Transactional
    public Transfer transferFunds(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new UnprocessableContentException("Transfer amount must be greater than 0");
        }

        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId)
                .orElseThrow(() -> new NotFoundException("Source account not found"));
        BankAccount toAccount = bankAccountRepository.findById(toAccountId)
                .orElseThrow(() -> new NotFoundException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new UnprocessableContentException("Insufficient funds in source account id: " + fromAccountId);
        }

        // Update balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        // Save updated accounts
        bankAccountRepository.saveAndFlush(fromAccount);
        bankAccountRepository.saveAndFlush(toAccount);

        // Create and persist the transfer record
        Transfer transfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .build();

        return transferRepository.saveAndFlush(transfer);
    }

    public BalanceResponse getBalance(UUID accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found for account ID: " + accountId));

        return BalanceResponse.builder()
                .accountId(account.getAccountId())    // Use getAccountId() to retrieve the UUID
                .accountNumber(String.valueOf(account.getAccountNumber()))
                .balance(account.getBalance())
                .bankName(account.getBank().getName())
                .build();
    }

    public List<TransferResponse> getTransferHistory(UUID accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        List<Transfer> transfers = transferRepository.findByFromAccountOrToAccount(account, account);
        return transfers.stream().map(t -> TransferResponse.builder()
                        .transferId(t.getTransferId())
                        .fromAccountId(t.getFromAccount().getAccountId())
                        .toAccountId(t.getToAccount().getAccountId())
                        .amount(t.getAmount())
                        .transferDate(t.getTransferDate())
                        .build())
                .collect(Collectors.toList());
    }

    public TransferResponse transferBalance(TransferRequest request){
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive");
        }

        // Retrieve accounts using their UUIDs
        BankAccount fromAccount = bankAccountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new NotFoundException("Source account not found"));
        BankAccount toAccount = bankAccountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new NotFoundException("Destination account not found"));

        // Check if source account has sufficient funds
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds in source account");
        }

        // Update balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        // Save updated accounts (optional if cascade or flush is properly configured)
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        // Create and persist the transfer record
        Transfer transfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(request.getAmount())
                .build();

        Transfer savedTransfer = transferRepository.saveAndFlush(transfer);

        return TransferResponse.builder()
                .transferId(savedTransfer.getTransferId())
                .fromAccountId(savedTransfer.getFromAccount().getAccountId())
                .toAccountId(savedTransfer.getToAccount().getAccountId())
                .amount(savedTransfer.getAmount())
                .transferDate(savedTransfer.getTransferDate())
                .build();
    }
}
