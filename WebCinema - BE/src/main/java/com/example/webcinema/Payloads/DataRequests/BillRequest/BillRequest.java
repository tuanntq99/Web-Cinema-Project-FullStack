package com.example.webcinema.Payloads.DataRequests.BillRequest;

import com.example.webcinema.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BillRequest {
    private int id;
    private double totalMoney;
    private String tradingCode;
    private Date createTime;
    private String name;
    private Date updateTime;
    private User users;
    private int promotion;
    private BillStatus billStatus;

    private List<FoodQuantityPair> foodItems;

    private List<TicketQuantityPair> ticketItems;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FoodQuantityPair {
        private int quantity;
        private int food;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TicketQuantityPair {
        private int quantity;
        private int ticket;
    }

}
