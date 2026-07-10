package com.example.offerops.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final  JwtService jwtService;




    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        String jwt = null;

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if ("jwt".equals(cookie.getName())) {

                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt==null){
            filterChain.doFilter(request,response);
            return;
        }
        try{



            if (SecurityContextHolder.getContext()
                    .getAuthentication() == null && jwtService.isTokenValid(jwt)){

                    Long id= jwtService.extractuserId(jwt);
                    String email= jwtService.extractUsername(jwt);
                    CustomUserDetails user=CustomUserDetails
                            .builder()
                            .authorities(List.of())
                            .id(id)
                            .email(email)
                            .build();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities());

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }






        } catch (Exception e) {
            SecurityContextHolder.clearContext();

        }

        filterChain.doFilter(request,response);
    }
}
