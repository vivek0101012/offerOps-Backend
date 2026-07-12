package com.example.offerops.configurations;


import com.example.offerops.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import com.example.offerops.security.Auth0LoginFailureHandler;
import com.example.offerops.security.Auth0LoginSuccessHandler;
@Configuration
public class SpringSecurity {


    private final CorsProperties corsProperties;
    private final JwtFilter jwtAuthFilter;

;private final Auth0LoginSuccessHandler auth0LoginSuccessHandler;
    private final Auth0LoginFailureHandler auth0LoginFailureHandler;


    public SpringSecurity(CorsProperties corsProperties, JwtFilter jwtAuthFilter, Auth0LoginSuccessHandler auth0LoginSuccessHandler, Auth0LoginFailureHandler auth0LoginFailureHandler) {
        this.corsProperties = corsProperties;
        this.jwtAuthFilter = jwtAuthFilter;

        this.auth0LoginSuccessHandler = auth0LoginSuccessHandler;
        this.auth0LoginFailureHandler = auth0LoginFailureHandler;
    }

    //FILTER CHAIN
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/api/auth/register", "/api/auth/login", "/error", "/api/internal/**", "/oauth2/**", "/login/oauth2/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()

                )   .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                )
                .addFilterBefore(
                jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(auth0LoginSuccessHandler)
                        .failureHandler(auth0LoginFailureHandler)
                ) .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            org.springframework.http.ResponseCookie cookie = org.springframework.http.ResponseCookie.from("jwt", "")
                                    .httpOnly(true)
                                    .secure(true)
                                    .path("/")
                                    .maxAge(0) // Expire immediately
                                    .sameSite("None")
                                    .build();
                            response.addHeader(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString());
                            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_OK);
                        })
                )

        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        configuration.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    //AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }





}
