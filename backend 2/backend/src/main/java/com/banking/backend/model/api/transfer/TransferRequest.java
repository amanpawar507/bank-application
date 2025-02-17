package com.banking.backend.model.api.transfer;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {
    @NonNull
    private UUID fromAccountId;

    @NonNull
    private UUID toAccountId;

    @NonNull
    private BigDecimal amount;

}
