package com.example.offerops.security;

import com.example.offerops.models.User;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {


    private final Long id;
    private final String email;
    private final String password;
    private  final  String  name;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.name = user.getName();
        this.authorities=List.of();
        this.id=user.getId();

    }

    @Override @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override @NullMarked
    public String getUsername() {
        return email;
    }


}
