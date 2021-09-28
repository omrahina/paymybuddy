package com.paymybuddy.paymybuddy.model;

import com.paymybuddy.paymybuddy.common.Constants;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "rate")
    private double rate;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "buddy_id", referencedColumnName = "id")
    private User buddy;

    public Transfer(User user, User buddy, BigDecimal amount, String description){
        this.user = user;
        this.buddy = buddy;
        this.amount = amount;
        this.rate = Constants.PERCENTAGE;
        this.description = description;
        this.date = LocalDate.now();
    }

    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
