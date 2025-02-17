package com.banking.backend.model.api.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {
    private UUID transferId;
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private OffsetDateTime transferDate;
}
