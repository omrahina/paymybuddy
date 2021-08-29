package com.paymybuddy.paymybuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",  unique=true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "balance", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal balance;

    @ManyToMany
    @JoinTable(name = "Relationship",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "buddy_id"))
    private List<User> buddies;

    @OneToMany(mappedBy = "user")
    private List<Transfer> transfers;

    @OneToMany(mappedBy = "buddy")
    private List<Transfer> received;

    @OneToMany(mappedBy = "user")
    private List<BankOperation> bankOperations;

    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.balance = BigDecimal.ZERO;
    }

    public void addBuddy(User buddy){
        buddies.add(buddy);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", bankAccount='" + bankAccount + '\'' + '}';
    }
}
