package com.example.webcinema.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Address")
    private String address;

    @Column(name = "Description")
    private String description;

    @Column(name = "Code")
    private String code;

    @Column(name = "NameOfCinema")
    private String nameOfCinema;

    @Column(name = "IsActive")
    private boolean isActive = true;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cinema")
    @JsonIgnoreProperties(value = "cinema")
    private List<Room> rooms;
}
