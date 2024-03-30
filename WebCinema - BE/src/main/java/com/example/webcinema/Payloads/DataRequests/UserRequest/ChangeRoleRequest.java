package com.example.webcinema.Payloads.DataRequests.UserRequest;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeRoleRequest {
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$")
    String email;
    @NonNull
    String role;
}
