package com.example.offerops.adapter;


import com.example.offerops.dto.ApplicationRequest;
import com.example.offerops.models.JobApplication;
import org.springframework.stereotype.Component;


@Component
public class CreateApplicationAdapter {


    public JobApplication toEntity(ApplicationRequest request){

        return JobApplication
                .builder()
                .company(request.getCompany())
                .role(request.getRole())
                .jdUrl(request.getJdUrl())
                .jdDescription(request.getJdDescription())
                .resumeUrl(request.getResumeUrl())
                .appliedDate(request.getAppliedDate())
                .deadlineDate(request.getDeadlineDate())
                .notes(request.getNotes())
                .source(request.getSource())
                .build();

    }
}
