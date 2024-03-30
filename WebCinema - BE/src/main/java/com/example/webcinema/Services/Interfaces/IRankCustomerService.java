package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.RankCustomer;

public interface IRankCustomerService {
    RankCustomer addNew(RankCustomer newRequest);

    RankCustomer remake(RankCustomer remakeRequest);

    RankCustomer delete(String name);
}
