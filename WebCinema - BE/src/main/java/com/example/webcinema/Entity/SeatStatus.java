package com.example.webcinema.Entity;

import com.example.webcinema.Entity.Enum.ESeatStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "SeatStatus")
public class SeatStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Code")
    private String code;

    @Column(name = "NameStatus")
    @Enumerated(EnumType.STRING)
    private ESeatStatus nameStatus;

    @OneToMany(mappedBy = "seatStatus")
    @JsonIgnoreProperties(value = "seatStatus")
    private List<Seat> seats;
}
