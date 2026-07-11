package com.example.offerops.controllers;


import com.example.offerops.dto.MessageResponse;
import com.example.offerops.services.EmailDigestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal")
public class InternalController {

    private final EmailDigestService emailDigestService;

    @Value("${app.internal.secret}")
    private String internalSecret;

    public InternalController(EmailDigestService emailDigestService) {
        this.emailDigestService = emailDigestService;
    }

    @PostMapping("/digest")
    public ResponseEntity<MessageResponse> triggerDigest(
            @RequestHeader("X-Internal-Secret") String secret) {

        if (!secret.equals(internalSecret)) {
            return ResponseEntity.status(403).build();
        }

        emailDigestService.sendWeeklyDigest();
        return ResponseEntity.ok(new MessageResponse("Digest triggered successfully"));
    }


}