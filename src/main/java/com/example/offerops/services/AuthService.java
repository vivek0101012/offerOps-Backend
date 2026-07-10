package com.example.offerops.services;


import com.example.offerops.adapter.UserDtoAdapter;
import com.example.offerops.dto.AuthResponse;
import com.example.offerops.dto.LoginRequest;
import com.example.offerops.dto.SignUpRequest;
import com.example.offerops.dto.UserDto;
import com.example.offerops.exception.UserAlreadyExistException;
import com.example.offerops.models.User;
import com.example.offerops.repositories.UserRepository;
import com.example.offerops.security.CustomUserDetails;
import com.example.offerops.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private  final UserRepository userRepository;
    private  final JwtService jwtService;
    private  final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private  final UserDtoAdapter userDtoAdapter;


    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDtoAdapter userDtoAdapter) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDtoAdapter = userDtoAdapter;
    }

    @Transactional
    public AuthResponse  register (SignUpRequest request){

        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistException("A user with this email already exists.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

       User savedUser= userRepository.save(user);



        UserDto userDto = userDtoAdapter.toDto(savedUser);
        String token= jwtService.generateToken(savedUser.getId(),savedUser.getEmail());

        return  AuthResponse.builder()
                .token(token)
                .userDto(userDto)
                .build();
    }

    @Transactional
    public  AuthResponse login(LoginRequest request){

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        CustomUserDetails user= (CustomUserDetails) authentication.getPrincipal();

        UserDto userDto= userDtoAdapter.toDto(user);

        String token = jwtService.generateToken(user.getId(), user.getEmail());


        return  AuthResponse.builder()
                .token(token)
                .userDto(userDto)
                .build();
    }



}
