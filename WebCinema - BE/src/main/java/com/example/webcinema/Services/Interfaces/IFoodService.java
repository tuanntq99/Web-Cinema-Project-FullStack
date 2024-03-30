package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Food;
import com.example.webcinema.Payloads.DataResponses.DataFood.SortFoodByQuantity;

import java.util.List;

public interface IFoodService {
    Food addNew(Food newFood);

    Food remake(Food remakeFood);

    Food delete(String name);

    List<SortFoodByQuantity> sortFoodByQuantity();
}
