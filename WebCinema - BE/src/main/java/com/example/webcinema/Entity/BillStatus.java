package com.example.webcinema.Entity;

import com.example.webcinema.Entity.Enum.EBillStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "BillStatus")
public class BillStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name")
    @Enumerated(EnumType.STRING)
    private EBillStatus name;

    @OneToMany(mappedBy = "billStatus")
    @JsonIgnoreProperties(value = "billStatus")
    private List<Bill> bills;

}
