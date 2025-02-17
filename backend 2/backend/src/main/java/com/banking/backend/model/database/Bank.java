package com.banking.backend.model.database;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Type;  // Import this

import javax.validation.constraints.NotBlank;
import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @JdbcTypeCode(Types.CHAR)
    @Column(name = "bank_id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID bankId;


    @NotBlank
    private String name;

    // One bank can have many bank accounts
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;

}
