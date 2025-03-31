package com.example.platform.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "refbalance")
public class RefBalanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double availableBalance;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }


}
