package com.example.webcinema.Payloads.Conveters;

import com.example.webcinema.Entity.User;
import com.example.webcinema.Payloads.DataRequests.UserRequest.SignUpRequest;
import com.example.webcinema.Payloads.DataResponses.DataUser.UserResponse;
import com.example.webcinema.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserResponse EntityToDTO(User user) {
        UserResponse dataResponseUser = modelMapper.map(user, UserResponse.class);
        dataResponseUser.setUserName(user.getUsername()); // check why mapper not work with userName
        Optional<User> optionalUser = userRepository.findByUserName(user.getUsername());
        optionalUser.ifPresent(foundUser -> {
            if (foundUser.getRole() != null) {
                dataResponseUser.setRoleName(foundUser.getRole().getRoleName().name());
            }
        });
        return dataResponseUser;
    }

    public User DTOtoEntity(SignUpRequest request) {
        return modelMapper.map(request, User.class);
    }
}
