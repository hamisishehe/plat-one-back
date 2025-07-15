package com.example.platform.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;



@Data
@Entity
@Table(name = "product_task")

public class TaskProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count_product;

    private LocalDateTime start_time;

    private LocalDateTime finish_time;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    private int total_amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;


    public enum Status {
        PENDING,
        COMPLETE,
    }

}
