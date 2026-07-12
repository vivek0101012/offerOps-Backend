package com.example.offerops.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Auth0LoginFailureHandler implements AuthenticationFailureHandler {

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // Print detailed logs to your IntelliJ console
        System.err.println("OAuth2 Login Failed: " + exception.getMessage());
        exception.printStackTrace();

        // Pass the error message to the frontend URL
        response.sendRedirect(redirectUri + "?error=oauth2_login_failed&message=" + java.net.URLEncoder.encode(exception.getMessage(), "UTF-8"));
    }
}