package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Ticket;

public interface ITicketService {
    Ticket addNew(Ticket newTicket);

    Ticket remake(Ticket remakeTicket);

    Ticket delete(String code);
}
