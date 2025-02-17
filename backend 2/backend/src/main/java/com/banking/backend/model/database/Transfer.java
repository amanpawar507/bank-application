package com.banking.backend.model.database;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @JdbcTypeCode(Types.CHAR)
    @Column(name = "transfer_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID transferId;

    // Source account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private BankAccount fromAccount;

    // Destination account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private BankAccount toAccount;

    @NotNull
    private BigDecimal amount;

    @CreationTimestamp
    private OffsetDateTime transferDate;

}

