package com.example.offerops.adapter;

import com.example.offerops.dto.UserDto;
import com.example.offerops.models.User;
import com.example.offerops.security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDtoAdapter {

    public UserDto toDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserDto toDto(CustomUserDetails user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}