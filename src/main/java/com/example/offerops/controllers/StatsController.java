package com.example.offerops.controllers;

import com.example.offerops.dto.SourceResponse;
import com.example.offerops.dto.SummaryResponse;
import com.example.offerops.dto.WeeklyResponse;
import com.example.offerops.services.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.xml.transform.Source;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@RequestMapping("/api/stats")
@RestController
public class StatsController {

    private Long userId=1L;//FOR TESTING ONLY

    private final  StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse>getSummary(){

        SummaryResponse response= statsService.getSummary(userId);

        return  ResponseEntity.ok(response);
    }
    @GetMapping("/source")
    public ResponseEntity<List<SourceResponse>> getSource(){
        List<SourceResponse> responses= statsService.getSource(userId);
        return  ResponseEntity.ok(responses);
    }

    @GetMapping("/weekly")
    public  ResponseEntity<List<WeeklyResponse>> getWeekly(){
        List<WeeklyResponse> responses= statsService.getWeekly(userId);
        return  ResponseEntity.ok(responses);
    }
}
