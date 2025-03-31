package com.example.platform.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "balance")
public class BalanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double availableBalance;

    private double lockedBalance;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

}
