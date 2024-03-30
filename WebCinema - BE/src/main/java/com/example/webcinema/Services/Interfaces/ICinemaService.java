package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Cinema;

public interface ICinemaService {
    Cinema addNew(Cinema newCinema);

    Cinema remake(Cinema remakeCinema);

    Cinema delete(String name);

}
