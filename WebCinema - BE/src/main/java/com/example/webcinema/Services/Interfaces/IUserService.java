package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Payloads.DataRequests.UserRequest.ChangePasswordRequest;
import com.example.webcinema.Payloads.DataRequests.UserRequest.ChangeRoleRequest;
import com.example.webcinema.Payloads.DataRequests.UserRequest.UpdateUserRequest;
import com.example.webcinema.Payloads.DataResponses.DataUser.UserResponse;
import com.example.webcinema.Payloads.Responses.MessageResponse;

import java.security.Principal;
import java.util.List;

public interface IUserService {
    MessageResponse changePassword(ChangePasswordRequest request, Principal connectedUser);

    MessageResponse changeRole(ChangeRoleRequest request);

    MessageResponse updateUser(UpdateUserRequest request);

    MessageResponse delete(String email);

    List<UserResponse> getAllUsers();

    List<UserResponse> getById(long id);

}
