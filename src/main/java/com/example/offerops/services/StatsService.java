package com.example.offerops.services;

import com.example.offerops.constant.ApplicationStatus;
import com.example.offerops.dto.SourceResponse;
import com.example.offerops.dto.SummaryResponse;
import com.example.offerops.dto.WeeklyResponse;
import com.example.offerops.models.JobApplication;
import com.example.offerops.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {
   private final   ApplicationRepository applicationRepository;

    public StatsService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    private List<JobApplication> getUserApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    public SummaryResponse getSummary(Long userId) {


        List<JobApplication> apps = getUserApplications(userId);
        // calculate from apps
        Long applicationCount= (long) apps.size();

        Long offerCount= apps.stream()
                .filter(
                        app->app.getStatus()==ApplicationStatus.OFFER
                )
                .count();
        Long activeCount= apps.stream()
                .filter(
                        app->app.getStatus()!=ApplicationStatus.REJECTED &&
                                app.getStatus()!=ApplicationStatus.OFFER
                )
                .count();

        Double offerPercentage = applicationCount == 0 ? 0.0 : (offerCount * 100.0) / applicationCount;
        SummaryResponse response = SummaryResponse.builder()
                .applicationCount(applicationCount)
                .activeCount(activeCount)
                .offerCount(offerCount)
                .offerPercentage(offerPercentage)
                .build();
        ;
        return  response;

    }



    public List<SourceResponse> getSource(Long userId){


        List<JobApplication> apps=getUserApplications(userId);
        return apps.stream()
                .collect(Collectors.groupingBy(
                        JobApplication::getSource,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> SourceResponse.builder()
                        .source(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .toList();


    }


    public  List<WeeklyResponse> getWeekly(Long userId){



        List<JobApplication> apps= getUserApplications(userId);
        return apps.stream()
                .collect(Collectors.groupingBy(
                        app -> app.getCreatedAt().getYear() + "-W" +
                                app.getCreatedAt().get(WeekFields.ISO.weekOfWeekBasedYear()),
                        LinkedHashMap::new,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> WeeklyResponse.builder()
                        .week(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .toList();

    }



}
