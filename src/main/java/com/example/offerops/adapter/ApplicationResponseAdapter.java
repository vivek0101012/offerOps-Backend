package com.example.offerops.adapter;

import com.example.offerops.dto.ApplicationResponse;
import com.example.offerops.models.JobApplication;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class ApplicationResponseAdapter {


    public ApplicationResponse toResponse(JobApplication jobApplication){

        return ApplicationResponse.builder()

                .id(jobApplication.getId())
                .company(jobApplication.getCompany())
                .role(jobApplication.getRole())
                .jdUrl(jobApplication.getJdUrl())
                .jdDescription(jobApplication.getJdDescription())
                .resumeUrl(jobApplication.getResumeUrl())
                .source(jobApplication.getSource())
                .status(jobApplication.getStatus())
                .appliedDate(jobApplication.getAppliedDate())
                .deadlineDate(jobApplication.getDeadlineDate())
                .notes(jobApplication.getNotes())
                .createdAt(jobApplication.getCreatedAt())
                .updatedAt(jobApplication.getUpdatedAt())
                .build();

    }

}
