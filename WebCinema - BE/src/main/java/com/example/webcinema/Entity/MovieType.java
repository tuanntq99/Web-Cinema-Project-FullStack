package com.example.webcinema.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "MovieType")
public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "MovieTypeName")
    private String movieTypeName;

    @Column(name = "IsActive")
    private boolean isActive;

    @OneToMany(mappedBy = "movieType")
    @JsonIgnoreProperties(value = "movieType")
    private List<Movie> movies;
}
