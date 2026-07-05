package com.example.offerops.adapter;


import com.example.offerops.dto.UpdateApplicationRequest;
import com.example.offerops.models.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class UpdateApplicationAdapter {

    public JobApplication toEntity(JobApplication app, UpdateApplicationRequest request){

        if (request.getCompany() != null) app.setCompany(request.getCompany());
        if (request.getRole() != null) app.setRole(request.getRole());
        if (request.getSource() != null) app.setSource(request.getSource());
        if (request.getAppliedDate() != null) app.setAppliedDate(request.getAppliedDate());
        if (request.getJdUrl() != null) app.setJdUrl(request.getJdUrl());
        if (request.getJdDescription() != null) app.setJdDescription(request.getJdDescription());
        if (request.getResumeUrl() != null) app.setResumeUrl(request.getResumeUrl());
        if (request.getDeadlineDate() != null) app.setDeadlineDate(request.getDeadlineDate());
        if (request.getNotes() != null) app.setNotes(request.getNotes());
        return app;
    }
}
