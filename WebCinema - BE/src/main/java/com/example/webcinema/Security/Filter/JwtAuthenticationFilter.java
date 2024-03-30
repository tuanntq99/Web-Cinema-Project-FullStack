package com.example.webcinema.Security.Filter;

import com.example.webcinema.Repository.UserRepository;
import com.example.webcinema.Security.Service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Skip filter for register/sign-in/request otp/refresh token
        if (request.getServletPath().contains("/api/v1/auth")
//                || request.getServletPath().contains("/api/v1/payment")
//                ||request.getServletPath().contains("/api/v1/")
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //lay jwt tu request
        String jwt = authHeader.substring(7);
        try {
            //Lay uesrName tu chuoi jwt (da build trong JwtTokenProvider)
            String userName = jwtTokenProvider.extractUserName(jwt);
            //Kiem tra user da duoc authenticate chua
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //Lay thong tin nguoi dung tu userId // default is load email
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                var user = userRepository.findByUserName(userDetails.getUsername())
                        .orElseGet(() -> userRepository.findByEmail(userDetails.getUsername())
                                .orElseThrow());
                //Check valid token and active userStatus and active account by confirm email
                try {
                    if (jwtTokenProvider.isTokenValid(jwt, userDetails)
                            && user.getUserStatus().getCode().equals("True") // active token
                            && user.isActive() // active account by email
                    ) {
                        //Neu nguoi dung hop le set thong tin cho security context
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));
                        // Set authentication for user
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    log.error("Token is not valid or account is inactive by email OTP: {}", e.getMessage());
                }
            }
        } catch (NullPointerException e) {
            log.error("Username is null or Authenticated: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
// Can not try catch. not response error message
}
