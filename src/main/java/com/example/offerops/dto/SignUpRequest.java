package com.example.offerops.dto;


import jakarta.validation.constraints.Email;
import lombok.*;


import jakarta.validation.constraints.NotBlank;
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpRequest {


    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be blank")

    private  String password;

    @NotBlank(message = "Password cannot be blank")

    private  String name;


}
