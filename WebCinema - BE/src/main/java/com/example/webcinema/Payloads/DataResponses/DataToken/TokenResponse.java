package com.example.webcinema.Payloads.DataResponses.DataToken;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    public String accessToken;
    public String refreshToken;
}
