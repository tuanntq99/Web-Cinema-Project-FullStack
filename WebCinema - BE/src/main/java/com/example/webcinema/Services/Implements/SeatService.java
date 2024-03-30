package com.example.webcinema.Services.Implements;

import com.example.webcinema.Config.ApplicationConfig;
import com.example.webcinema.Entity.Seat;
import com.example.webcinema.Repository.SeatRepository;
import com.example.webcinema.Repository.TicketRepository;
import com.example.webcinema.Services.Interfaces.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ApplicationConfig config;

    @Override
    public Seat addNew(Seat newSeat) {
        return seatRepository.save(newSeat);
    }

    @Override
    public Seat remake(Seat remakeSeat) {
        var current = seatRepository.findById(remakeSeat.getId())
                .orElseThrow(() -> new RuntimeException("Data not found"));
        BeanUtils.copyProperties(remakeSeat, current, config.getNullPropertyNames(remakeSeat));
        return seatRepository.save(current);
    }

    @Override
    public Seat delete(int id) {
        var current = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));
        ticketRepository.findAll().forEach(x -> {
            if (Integer.valueOf(id).equals(x.getSeats().getId())) x.setSeats(null);
        });
        seatRepository.delete(current);
        return current;
    }
}
