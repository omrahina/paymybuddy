package com.paymybuddy.paymybuddy.Dto;

import com.paymybuddy.paymybuddy.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    private User user;
    private String connection;
    private BigDecimal amount;
    private String description;
    private String bankAccount;
    private boolean toMyBankAccount;
    private boolean fromMyBankAccount;

    public TransferDTO(User user){
        this.user = user;
    }

    public TransferDTO(User user, String connection, BigDecimal amount){
        this(user);
        this.connection = connection;
        this.amount = amount;
    }

    public TransferDTO(User user, BigDecimal amount, boolean toMyBankAccount, boolean fromMyBankAccount){
        this(user);
        this.amount = amount;
        this.toMyBankAccount = toMyBankAccount;
        this.fromMyBankAccount = fromMyBankAccount;
    }
}
