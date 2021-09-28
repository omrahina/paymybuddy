package com.paymybuddy.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    private String firstName;

    private String lastName;

    private String bankAccount;

    private String newPassword;

    public UserDTO(String email, String firstName, String lastName, String bankAccount){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
    }

    public UserDTO(String email, String password){
        this.email = email;
        this.password = password;
    }
}
