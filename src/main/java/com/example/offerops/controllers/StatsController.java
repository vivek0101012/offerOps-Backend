package com.example.offerops.controllers;

import com.example.offerops.dto.SourceResponse;
import com.example.offerops.dto.SummaryResponse;
import com.example.offerops.dto.WeeklyResponse;
import com.example.offerops.security.CustomUserDetails;
import com.example.offerops.services.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @AuthenticationPrincipal CustomUserDetails user) {

        SummaryResponse response =
                statsService.getSummary(user.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/source")
    public ResponseEntity<List<SourceResponse>> getSource(
            @AuthenticationPrincipal CustomUserDetails user) {

        List<SourceResponse> responses =
                statsService.getSource(user.getId());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<WeeklyResponse>> getWeekly(
            @AuthenticationPrincipal CustomUserDetails user) {

        List<WeeklyResponse> responses =
                statsService.getWeekly(user.getId());

        return ResponseEntity.ok(responses);
    }
}