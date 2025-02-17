package com.banking.backend.model.database;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import javax.validation.constraints.NotBlank;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @JdbcTypeCode(Types.CHAR)
    @Column(name = "customer_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID customerId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    // One customer can have multiple accounts
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;
}
