package com.paymybuddy.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "User")
@Data
public class User {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToMany
    @JoinTable(name = "Relationship",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "buddy_email"))
    private List<User> buddies;

    @OneToMany(mappedBy = "buddy")
    private List<Transfer> transfers;

    @OneToMany(mappedBy = "user")
    private List<Transfer> received;
}
