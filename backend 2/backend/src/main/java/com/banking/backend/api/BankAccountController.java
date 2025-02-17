package com.banking.backend.api;

import com.banking.backend.model.api.account.CreateAccountRequest;
import com.banking.backend.model.api.account.CreateAccountResponse;
import com.banking.backend.model.api.balance.BalanceResponse;
import com.banking.backend.model.api.transfer.TransferRequest;
import com.banking.backend.model.api.transfer.TransferResponse;
import com.banking.backend.service.BankAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    // Endpoint to create a new bank account with an initial deposit
    @Transactional
    @PostMapping("/create-account")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        CreateAccountResponse createAccountResponse = bankAccountService.createAccount(request);
        return ResponseEntity.ok(createAccountResponse);
    }


    // Endpoint to retrieve the balance for a given account
    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {
        BalanceResponse balanceResponse = bankAccountService.getBalance(accountId);
        return ResponseEntity.ok(balanceResponse);
    }

    @Transactional
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferFunds(@RequestBody TransferRequest request) {
        TransferResponse transferResponse = bankAccountService.transferBalance(request);
        return ResponseEntity.ok(transferResponse);
    }


    // Endpoint to retrieve the transfer history for a given account
    @GetMapping("/transfer-history/{accountId}")
    public ResponseEntity<List<TransferResponse>> getTransferHistory(@PathVariable UUID accountId) {
        List<TransferResponse> transfers = bankAccountService.getTransferHistory(accountId);
        return ResponseEntity.ok(transfers);
    }
}
