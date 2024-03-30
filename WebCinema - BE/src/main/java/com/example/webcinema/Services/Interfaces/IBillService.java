package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Payloads.DataRequests.BillRequest.BillRequest;
import com.example.webcinema.Payloads.Responses.MessageResponse;

public interface IBillService {
    String addNew(BillRequest newBill, String request);

    String remake(BillRequest remakeBill, String request);

    MessageResponse delete(String name);
}
