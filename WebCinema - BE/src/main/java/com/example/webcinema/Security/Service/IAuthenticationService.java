package com.example.webcinema.Security.Service;

import com.example.webcinema.Payloads.DataRequests.TokenRequest.RefreshTokenRequest;
import com.example.webcinema.Payloads.DataRequests.UserRequest.LoginRequest;
import com.example.webcinema.Payloads.DataRequests.UserRequest.SignUpRequest;
import com.example.webcinema.Payloads.DataResponses.DataToken.TokenResponse;
import com.example.webcinema.Payloads.DataResponses.DataUser.UserResponse;
import com.example.webcinema.Payloads.Responses.MessageResponse;

import java.util.List;

public interface IAuthenticationService {
    MessageResponse registerUser(SignUpRequest request);

    TokenResponse loginUser(LoginRequest request);

    TokenResponse refreshToken(RefreshTokenRequest request);

    MessageResponse verify(String email, String otp);

    String regenerateOtp(String email);

    MessageResponse forgotPassword(String email);

    MessageResponse resetPassword(String otp);

}
