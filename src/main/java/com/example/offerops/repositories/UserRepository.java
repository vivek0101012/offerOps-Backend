package com.example.offerops.repositories;


import com.example.offerops.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotBlank(message = "Email cannot be blank") String email);
}
