package com.banking.backend.model.api.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountResponse {
    private UUID accountId;
    private String accountNumber;
    private BigDecimal balance;
    private String bankName;
    private String firstName;
    private String lastName;
}
