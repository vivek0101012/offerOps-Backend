package com.example.offerops.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {


    @NotBlank(message = "Email cannot be blank")
    private  String email;

    @NotBlank(message = "Password cannot be blank")
    private  String password;
}