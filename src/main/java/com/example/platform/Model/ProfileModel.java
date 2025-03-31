package com.example.platform.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "profile")
public class ProfileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String secondName;

    private String lastName;

    private String city;

    private String zipCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;


}
