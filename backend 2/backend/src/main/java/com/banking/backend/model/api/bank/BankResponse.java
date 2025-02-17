package com.banking.backend.model.api.bank;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankResponse {
    private UUID bankId;
    private String name;
}
