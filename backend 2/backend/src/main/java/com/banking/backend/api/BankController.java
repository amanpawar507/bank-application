package com.banking.backend.api;

import com.banking.backend.model.api.bank.BankResponse;
import com.banking.backend.model.api.bank.CreateBankRequest;
import com.banking.backend.model.database.Bank;
import com.banking.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @PostMapping("/create-bank")
    public ResponseEntity<BankResponse> createBank(@Valid @RequestBody CreateBankRequest request) {
        BankResponse bankResponse = bankService.createBank(request.getName());
        return ResponseEntity.ok(bankResponse);
    }
}
