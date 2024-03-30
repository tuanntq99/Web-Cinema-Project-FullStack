package com.example.webcinema.Security.Service;

import com.example.webcinema.Entity.*;
import com.example.webcinema.Entity.Enum.ERole;
import com.example.webcinema.Handle.Email.Validate;
import com.example.webcinema.Payloads.Conveters.UserConverter;
import com.example.webcinema.Payloads.DataRequests.UserRequest.LoginRequest;
import com.example.webcinema.Payloads.DataRequests.UserRequest.SignUpRequest;
import com.example.webcinema.Payloads.DataRequests.TokenRequest.RefreshTokenRequest;
import com.example.webcinema.Payloads.DataResponses.DataToken.TokenResponse;
import com.example.webcinema.Payloads.Responses.MessageResponse;
import com.example.webcinema.Repository.*;
import com.example.webcinema.Services.Implements.EmailService;
import com.example.webcinema.Services.Implements.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserConverter userConverter;
    private final RankCustomerRepository rankCustomerRepository;
    private final UserStatusRepository userStatusRepository;
    private final EmailService emailService;
    private final ConfirmEmailRepository confirmEmailRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.email-verify.expiration}")
    private long emailExpiration;

    //Register Service
    @Override
    public MessageResponse registerUser(SignUpRequest request) {
        if (request.getUserName() == null || request.getUserName().trim().isEmpty()
                || request.getName() == null || request.getName().trim().isEmpty()
                || request.getEmail() == null || request.getEmail().trim().isEmpty()
                || request.getPassword() == null || request.getPassword().trim().isEmpty()
                || request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()
        ) {
            return new MessageResponse("Error: Fill-up information!");
        }
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            return new MessageResponse("Error: UserName already exist");
        }
        if (!Validate.isValidEmail(request.getEmail())) {
            return new MessageResponse("Error: Email format is invalid");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new MessageResponse("Error: Email already exist");
        }
        if (!Validate.isValidPhoneNumber(request.getPhoneNumber())) {
            return new MessageResponse("Error: PhoneNumber format is invalid");
        }
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            return new MessageResponse("Error: PhoneNumber already exist");
        }
        User user = userConverter.DTOtoEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setUserStatus(userStatusRepository
                .findByCode("False")
                .orElseThrow(() -> new RuntimeException("Not found UserStatus")));

        rankCustomerRepository.findAll().forEach(x -> {
            if (Integer.valueOf(request.getPoint()).equals(x.getPoint()))
                user.setRankCustomer(x);
        });

        //Default User
        user.setRole(roleRepository.findByRoleName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error:Role is not found."))
        );

        userRepository.save(user);

        createConfirmEmail(user);
        var email = createConfirmEmail(user);
        sendVerificationEmail(request.getEmail(), email.getConfirmCode()); //Off function temporary

        return new MessageResponse("Email sent... please verify account within 5 minute!");

    }

    public ConfirmEmail createConfirmEmail(User user) {
        ConfirmEmail email = new ConfirmEmail();
        email.setUsers(user);

        //Format requiredTime
        Date expirationDate = new Date(emailExpiration);
        expirationDate.setTime(expirationDate.getTime() - (8 * 60 * 60 * 1000));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String formattedExpiration = formatter.format(expirationDate);
        email.setRequiredTime(formattedExpiration);

        email.setExpiredTime(new Date(System.currentTimeMillis() + emailExpiration));
        email.setConfirmCode(generateOTP());
        confirmEmailRepository.save(email);
        return email;
    }

    //Login Service
    @Override
    public TokenResponse loginUser(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword())
        );

        //Test if userName = userName or email

        var user = userRepository.findByUserName(request.getUserName())
                .orElseGet(() -> userRepository.findByEmail(request.getUserName())
                        .orElseThrow());
        user.setUserStatus(userStatusRepository
                .findByCode("True")
                .orElseThrow(() -> new RuntimeException("Not found UserStatus")));
        var jwtToken = jwtTokenProvider.generateToken(user);
//        var refreshToken = jwtTokenProvider.generateRefreshToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(request.getUserName());
//        saveUserToken(user, jwtToken);
        return TokenResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    //Refresh Token
    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        var currentToken = refreshTokenService.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));

        var accessToken = jwtTokenProvider.generateToken(currentToken.getUsers());
        var refreshToken = UUID.randomUUID().toString();
        currentToken.setToken(refreshToken);
        currentToken.setExpiredTime(new Date(System.currentTimeMillis() + refreshExpiration));
        refreshTokenRepository.save(currentToken);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //verify mail
    @Override
    public MessageResponse verify(String email, String otp) {
        var currentEmail = confirmEmailRepository.findByConfirmCode(otp)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (currentEmail.getUsers().getEmail().isEmpty()) {
            return new MessageResponse("User not found");
        } else if (currentEmail.isConfirm()) {
            return new MessageResponse("User is already verified");
        } else if (currentEmail.getUsers().getEmail().equals(email) &&
                currentEmail.getExpiredTime().compareTo(new Date()) > 0) {
            currentEmail.setConfirm(true);
            currentEmail.getUsers().setActive(true);
            confirmEmailRepository.save(currentEmail);
            return new MessageResponse("Sign-up Success !");
        } else {
            currentEmail.setConfirmCode(email);
            confirmEmailRepository.save(currentEmail);
            return new MessageResponse("Expired Time! Please regenerate otp and try again");
        }
    }

    //ReSend OTP
    @Override
    public String regenerateOtp(String email) {
        var emailExpired = confirmEmailRepository.findByConfirmCode(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        var otp = generateOTP();
        sendVerificationEmail(email, otp);
        emailExpired.setConfirmCode(otp);
        emailExpired.setExpiredTime(new Date(System.currentTimeMillis() + 1000 * 60));
        confirmEmailRepository.save(emailExpired);
        return "Email sent... please verify account within 1 minute";
    }

    //Forgot Password
    @Override
    public MessageResponse forgotPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        var confirmEmail = createConfirmEmail(user);
        sendVerificationEmail(email, confirmEmail.getConfirmCode());
        return new MessageResponse("Email sent... please get OTP within 5 minute");
    }

    //Reset Password
    @Override
    public MessageResponse resetPassword(String otp) {
        var currentConfirmEmail = confirmEmailRepository.findByConfirmCode(otp)
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        var user = userRepository.findByConfirmEmails(currentConfirmEmail)
                .orElseThrow(() -> new RuntimeException("Data not found !"));
        var newPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        confirmEmailRepository.delete(currentConfirmEmail);
        sendNewPassword(user.getEmail(), newPassword);
        return new MessageResponse("Change password success ! Please get new password in your email.");
    }

    //send mail new Password
    private void sendNewPassword(String email, String newPassword) {
        String subject = "Reset Password";
        String body = "your new password is: " + newPassword;
        emailService.sendEmail(email, subject, body);
    }

    //Generate Password
    public static String generateRandomPassword() {
        var uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 6);
    }

    //generate OTP verify mail
    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    //send mail verify
    private void sendVerificationEmail(String email, String otp) {
        String subject = "Email verification";
        String body = "your verification otp is: " + otp;
        emailService.sendEmail(email, subject, body);
    }

}
