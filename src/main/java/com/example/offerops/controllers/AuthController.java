package com.example.offerops.controllers;


import com.example.offerops.dto.AuthResponse;
import com.example.offerops.dto.LoginRequest;
import com.example.offerops.dto.SignUpRequest;
import com.example.offerops.dto.UserDto;
import com.example.offerops.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.time.Duration;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private  final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> signUp(@Valid  @RequestBody SignUpRequest request){

        AuthResponse response=authService.register(request);

        ResponseCookie cookie=ResponseCookie.from("jwt",response.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("strict")
                .build();

        return  ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(response.getUserDto());
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> Login(@Valid @RequestBody LoginRequest request){

        AuthResponse response= authService.login(request);

        ResponseCookie cookie=ResponseCookie.from("jwt",response.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("strict")
                .build();

        return  ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(response.getUserDto());

    }


}
