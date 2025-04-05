package com.example.platform.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDateTime startDate;

    private LocalDateTime maturityDate;

    @Enumerated(EnumType.STRING)
    private InvestmentPackage investmentPackage;

    // New field for status
    @Enumerated(EnumType.STRING)
    private InvestmentStatus status;

    public enum InvestmentPackage {
            COMMON
    }

    // New enum for status
    public enum InvestmentStatus {
        RUNNING, FINISHED
    }


    // Method to calculate daily profit based on the package with specified rates
    public double calculateDailyProfit() {
        double profitRate;

        switch (investmentPackage) {
            case COMMON:
                profitRate = 0.025;  // 2%
                break;
//            case RARE:
//                profitRate = 0.0025; // 2.5%
//                break;
//            case EPIC:
//                profitRate = 0.003;  // 3%
//                break;
//            case PREMIUM1:
//                profitRate = 0.003; // 3.5%
//                break;
//            case PREMIUM2:
//                profitRate = 0.003;  // 4%
//                break;
//            case PREMIUM3:
//                profitRate = 0.003; // 3.5%
//                break;
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
        return LocalDateTime.now().isAfter(maturityDate); // Updated to use LocalDateTime
    }
}
