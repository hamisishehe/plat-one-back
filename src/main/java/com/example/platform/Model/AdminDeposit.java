package com.example.platform.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "amindeposit")
public class AdminDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private String username;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private DepositModel.Status status;

    private String gateAway;

    private String address;

    private String phoneNumber;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;


    public enum Status {
        PENDING,
        CONFIRMED,
        REJECTED
    }

}
