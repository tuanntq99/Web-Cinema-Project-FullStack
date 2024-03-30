package com.example.webcinema.Repository;

import com.example.webcinema.Entity.Bill;
import com.example.webcinema.Entity.BillTicket;
import com.example.webcinema.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillTicketRepository extends JpaRepository<BillTicket, Integer> {
    Optional<BillTicket> findBillTicketByBillsAndTickets(Bill bill, Ticket ticket);
}
