package com.example.webcinema.Repository;

import com.example.webcinema.Entity.ConfirmEmail;
import com.example.webcinema.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByConfirmEmails(ConfirmEmail confirmEmail);
}
