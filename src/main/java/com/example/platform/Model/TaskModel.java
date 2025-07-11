package com.example.platform.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count_product;

    private LocalDateTime start_time;

    private LocalDateTime finish_time;

    private int total_amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductModel product;

    private LocalDateTime completedAt;
}
