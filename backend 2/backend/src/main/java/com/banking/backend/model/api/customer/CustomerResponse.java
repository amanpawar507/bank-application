package com.banking.backend.model.api.customer;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private OffsetDateTime createdAt;
}
