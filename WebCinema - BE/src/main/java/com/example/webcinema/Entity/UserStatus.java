package com.example.webcinema.Entity;

import com.example.webcinema.Entity.Enum.EUserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "UserStatus")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    @Enumerated(EnumType.STRING)
    private EUserStatus name;

    @OneToMany(mappedBy = "userStatus")
    @JsonIgnoreProperties(value = "userStatus")
    private List<User> users;

}
