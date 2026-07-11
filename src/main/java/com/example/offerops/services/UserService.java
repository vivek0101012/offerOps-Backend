package com.example.offerops.services;

import com.example.offerops.adapter.UserDtoAdapter;
import com.example.offerops.dto.ChangePasswordRequest;
import com.example.offerops.dto.UpdateProfileRequest;
import com.example.offerops.dto.UserDto;
import com.example.offerops.exception.UserAlreadyExistException;
import com.example.offerops.exception.UserNotFoundException;
import com.example.offerops.models.User;
import com.example.offerops.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoAdapter userDtoAdapter;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserDtoAdapter userDtoAdapter) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoAdapter = userDtoAdapter;
    }

    @Transactional
    public UserDto updateProfile(
            Long userId,
            UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail())) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new UserAlreadyExistException(
                        "Email already exists.");
            }

            user.setEmail(request.getEmail());
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        User savedUser = userRepository.save(user);

        return userDtoAdapter.toDto(savedUser);
    }

    @Transactional
    public UserDto changePassword(
            Long userId,
            ChangePasswordRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Old password is incorrect.");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );
        User savedUser = userRepository.save(user);

        return userDtoAdapter.toDto(savedUser);
    }
}