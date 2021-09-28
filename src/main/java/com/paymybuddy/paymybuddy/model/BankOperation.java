package com.paymybuddy.paymybuddy.model;


import com.paymybuddy.paymybuddy.common.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Bank_Operation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankOperation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "type")
    private Operation type;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public BankOperation(User user, String bankAccount, BigDecimal amount, Operation type, String description){
        this.user = user;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = LocalDate.now();
    }
}
