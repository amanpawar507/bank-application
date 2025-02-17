package com.banking.backend.model.api.account;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @NonNull
    private UUID customerId;

    @NotNull
    private BigDecimal initialDeposit;

    @NotNull
    private UUID bankId;

}
