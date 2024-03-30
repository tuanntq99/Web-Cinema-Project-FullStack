package com.example.webcinema.Entity;

import com.example.webcinema.Entity.Enum.ERole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Code")
    private String code;

    @Column(name = "RoleName")
    @Enumerated(EnumType.STRING)
    private ERole roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;

}
