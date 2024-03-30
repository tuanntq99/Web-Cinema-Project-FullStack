package com.example.webcinema.Payloads.DataResponses.DataFood;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortFoodByQuantity {
    private int id;
    private String name;
    private int quantity;
}
