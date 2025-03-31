package com.example.platform.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "investment")
public class InvestmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    private double amount;

    private LocalDate startDate;

    private LocalDate maturityDate;

    @Enumerated(EnumType.STRING)
    private InvestmentPackage investmentPackage;

    // New field for status
    @Enumerated(EnumType.STRING)
    private InvestmentStatus status;

    public enum InvestmentPackage {
        ENA1, ENA2, ENA3, ENA4, ENA5
    }

    // New enum for status
    public enum InvestmentStatus {
        RUNNING, FINISHED
    }


    // Method to calculate daily profit based on the package with specified rates
    public double calculateDailyProfit() {
        double profitRate;

        switch (investmentPackage) {
            case ENA1:
                profitRate = 0.005;  // 2%
                break;
            case ENA2:
                profitRate = 0.0065; // 2.5%
                break;
            case ENA3:
                profitRate = 0.0075;  // 3%
                break;
            case ENA4:
                profitRate = 0.0085; // 3.5%
                break;
            case ENA5:
                profitRate = 0.01;  // 4%
                break;
            default:
                throw new IllegalArgumentException("Unknown investment package");
        }

        return amount * profitRate; // Daily profit calculation
    }

    // Check if investment is mature and update status
    public void updateStatus() {
        if (isMature()) {
            this.status = InvestmentStatus.FINISHED; // Change status to FINISHED if mature
        }
    }

    // Check if investment is mature
    public boolean isMature() {
        return LocalDate.now().isAfter(maturityDate);
    }
}
