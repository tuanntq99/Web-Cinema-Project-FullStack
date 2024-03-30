package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Seat;

public interface ISeatService {
    Seat addNew(Seat newSeat);

    Seat remake(Seat remakeSeat);

    Seat delete(int id);
}
