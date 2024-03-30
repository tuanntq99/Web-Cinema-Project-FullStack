package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.BillFood;

public interface IBillFoodService {
    BillFood addNew(BillFood newBillFood);

    BillFood remake(BillFood remakeBilLFood);

    BillFood delete(int id);
}
