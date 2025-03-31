package com.example.platform.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ena_token_balance")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class EnaTokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double availableBalance;

    private double lockedBalance;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    public void addProfit(double amount) {
        this.availableBalance += amount;
    }

}
