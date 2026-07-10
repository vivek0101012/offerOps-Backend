package com.example.offerops.controllers;

import com.example.offerops.dto.ChangePasswordRequest;
import com.example.offerops.dto.UpdateProfileRequest;
import com.example.offerops.dto.UserDto;
import com.example.offerops.security.CustomUserDetails;
import com.example.offerops.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(

            @AuthenticationPrincipal
            CustomUserDetails user,

            @RequestBody
            UpdateProfileRequest request) {


        UserDto response =
                userService.updateProfile(
                        user.getId(),
                        request
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<UserDto> changePassword(

            @AuthenticationPrincipal
            CustomUserDetails user,

            @RequestBody
            @Valid
            ChangePasswordRequest request) {

        UserDto response =
                userService.changePassword(
                        user.getId(),
                        request
                );

        return ResponseEntity.ok(response);
    }
}