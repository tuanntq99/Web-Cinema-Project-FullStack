package com.example.webcinema.Payloads.DataRequests.UserRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
