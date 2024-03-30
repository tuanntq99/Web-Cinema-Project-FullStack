package com.example.webcinema.Payloads.DataResponses.DataUser;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int point;
    private String userName;
    private String email;
    private String name;
    private String phoneNumber;
    private String roleName;
}
