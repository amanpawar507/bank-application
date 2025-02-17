package com.banking.backend.model.database;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import lombok.Generated;
import org.hibernate.annotations.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @JdbcTypeCode(Types.CHAR)
    @Column(name = "account_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID accountId;

    @Column(unique = true)
    @NotNull
    private Long accountNumber;

    @NotNull
    private BigDecimal balance;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    // Many accounts belong to one customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // New relationship: each bank account belongs to a bank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    // Optional: Relationships for transfers
    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private List<Transfer> sentTransfers;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private List<Transfer> receivedTransfers;

}
