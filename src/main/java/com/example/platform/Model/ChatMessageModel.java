package com.example.platform.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ChatMessageModel {

    @Id
    @GeneratedValue
    private Long id;

    private Long senderId;
    private Long receiverId;

    private String message;

    private boolean replied;

    private LocalDateTime timestamp;

}
