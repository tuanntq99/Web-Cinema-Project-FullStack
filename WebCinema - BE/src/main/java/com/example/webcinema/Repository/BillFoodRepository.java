package com.example.webcinema.Repository;

import com.example.webcinema.Entity.Bill;
import com.example.webcinema.Entity.BillFood;
import com.example.webcinema.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillFoodRepository extends JpaRepository<BillFood, Integer> {
    Optional<BillFood> findBillFoodByBillsAndFood(Bill bill, Food food);

}
