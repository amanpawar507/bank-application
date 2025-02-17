package com.banking.backend.model.api.balance;

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
public class BalanceResponse {
    private UUID accountId;
    private String accountNumber;
    private String bankName;
    private BigDecimal balance;
}
