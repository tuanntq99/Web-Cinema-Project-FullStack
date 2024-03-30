package com.example.webcinema.Repository;

import com.example.webcinema.Entity.RankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankCustomerRepository extends JpaRepository<RankCustomer, Integer> {
    Optional<RankCustomer> findByName(String name);
}
