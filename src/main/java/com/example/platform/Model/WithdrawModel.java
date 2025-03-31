package com.example.platform.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "withdraw")
public class WithdrawModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    private double amount;

    private String transactionId;

    private LocalDate withdrawalDate;

    private String phoneNumber;

    private String withdrawMethod;

    private String wallet;

    @Enumerated(EnumType.STRING)
    private Status status;

    public WithdrawModel() {

        this.withdrawalDate = LocalDate.now(); // Set the withdrawal date to the current date by default
    }

    public enum Status{
        PENDING,PAYED,LOCKED
    }
}
