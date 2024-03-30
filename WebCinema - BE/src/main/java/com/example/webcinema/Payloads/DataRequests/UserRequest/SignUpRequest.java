package com.example.webcinema.Payloads.DataRequests.UserRequest;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @Min(value = 0, message = "Value must be greater than or equal to 0")
    @Max(value = 100, message = "Value must be less than or equal to 100")
    private int point;
    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$")
    private String email;
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    @Pattern(regexp = "0[0-9]{9}")
    private String phoneNumber;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
