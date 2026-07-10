package com.example.offerops.security;

import com.example.offerops.models.User;
import com.example.offerops.repositories.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private  final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public  @NullMarked  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepository.findByEmail(username);

        if (user==null) throw new UsernameNotFoundException("No user exist");
        return  new CustomUserDetails(user);

    }
}
