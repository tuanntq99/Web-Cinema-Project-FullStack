package com.example.webcinema.Repository;

import com.example.webcinema.Entity.Enum.ERole;
import com.example.webcinema.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    //    Optional trong truong hop rong
    Optional<Role> findByRoleName(ERole roleName);
}
