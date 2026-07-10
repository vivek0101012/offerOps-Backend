package com.example.offerops.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthResponse {

    private  String token;
    private UserDto userDto;

}
