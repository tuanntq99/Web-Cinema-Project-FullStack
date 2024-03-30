package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.BillTicket;

public interface IBillTicketService {
    BillTicket addNew(BillTicket newBillTicket);

    BillTicket remake(BillTicket remakeBillTicket);

    BillTicket delete(int id);
}
