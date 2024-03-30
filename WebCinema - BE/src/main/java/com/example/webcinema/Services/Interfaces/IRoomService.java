package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Room;

public interface IRoomService {
    Room addNew(Room newRoom);

    Room remake(Room remakeRoom);

    Room delete(String name);
}
