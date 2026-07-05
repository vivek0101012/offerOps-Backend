package com.example.offerops.services;


import com.example.offerops.adapter.ApplicationResponseAdapter;
import com.example.offerops.adapter.CreateApplicationAdapter;
import com.example.offerops.adapter.StatusHistoryResponseAdapter;
import com.example.offerops.adapter.UpdateApplicationAdapter;
import com.example.offerops.dto.*;
import com.example.offerops.exception.JobApplicationNotFound;
import com.example.offerops.models.JobApplication;
import com.example.offerops.models.StatusHistory;
import com.example.offerops.models.User;
import com.example.offerops.repositories.ApplicationRepository;
import com.example.offerops.repositories.StatusHistoryRepository;
import org.springframework.data.util.TypeCollector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServices  {


    private final ApplicationRepository applicationRepository;
    private final CreateApplicationAdapter createApplicationAdapter;
    private  final UpdateApplicationAdapter updateApplicationAdapter;
    private  final  ApplicationResponseAdapter applicationResponseAdapter;
    private  final StatusHistoryRepository statusHistoryRepository;
    private  final StatusHistoryResponseAdapter statusHistoryResponseAdapter;

    public ApplicationServices(
            ApplicationRepository applicationRepository,
            CreateApplicationAdapter createApplicationAdapter,
            UpdateApplicationAdapter updateApplicationAdapter,
            ApplicationResponseAdapter applicationResponseAdapter, StatusHistoryRepository statusHistoryRepository, StatusHistoryResponseAdapter statusHistoryResponseAdapter
    ) {
        this.applicationRepository = applicationRepository;
        this.createApplicationAdapter = createApplicationAdapter;
        this.updateApplicationAdapter = updateApplicationAdapter;
        this.applicationResponseAdapter = applicationResponseAdapter;
        this.statusHistoryRepository = statusHistoryRepository;
        this.statusHistoryResponseAdapter = statusHistoryResponseAdapter;
    }

    public ApplicationResponse createApplication(ApplicationRequest request, Long userId){

        //user validation logic

        User user= new User();

        //Request to job application
        JobApplication jobApplication= createApplicationAdapter.toEntity(request);

        jobApplication.setUser(user);

        applicationRepository.save(jobApplication);

        //job application to response Dto

        return applicationResponseAdapter.toResponse(jobApplication);

    }


    public List<ApplicationResponse> getAllApplications(Long userId){

        return applicationRepository.findByUserId(userId)
                .stream()
                .map(applicationResponseAdapter::toResponse)
                .toList();
    }

    // private used internally avoid repetitive  logic
    private JobApplication findApplicationById(Long id, Long userId) {
        return applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new JobApplicationNotFound("No such job application found"));
    }

    public ApplicationResponse getApplicationById(Long id,Long userId){

        return applicationResponseAdapter.toResponse(findApplicationById(id, userId));
    }





    //update
    public ApplicationResponse updateApplication(Long id,Long userId,UpdateApplicationRequest request){


        //validate user

        //find the application
        JobApplication jobApplication = findApplicationById(id, userId);

        jobApplication  = updateApplicationAdapter.toEntity(jobApplication,request);

        applicationRepository.save(jobApplication);

        return  applicationResponseAdapter.toResponse(jobApplication);

    }

    //update status we will do later
    public  ApplicationResponse updateStatus(Long id, Long userId, UpdateStatusRequest request){

        //user validation

        //job application validation
        JobApplication jobApplication=findApplicationById(id, userId);

        //validate the status logic ;

        //update the status
        StatusHistory history = StatusHistory.builder()
                .jobApplication(jobApplication)
                .fromStatus(jobApplication.getStatus())
                .toStatus(request.getStatus())
                .build();
        statusHistoryRepository.save(history);
        jobApplication.setStatus(request.getStatus());

        //save
        jobApplication=applicationRepository.save(jobApplication);

        //update the status history for the job application

        //transform and return
        return  applicationResponseAdapter.toResponse(jobApplication);

    }


    //delete
    public  void   deleteApplication(Long userId,Long id){

        //user verifications based on token

        //find application based on user id
        JobApplication jobApplication = findApplicationById(id, userId);

        applicationRepository.delete(jobApplication);


    }


    //get Application service history

    public  List<StatusHistoryResponse> getStatusHistory(Long id,Long userId){

        //validate the user

        //find the job application
        JobApplication jobApplication = findApplicationById(id,userId);

        // get the history
        List<StatusHistory> statusHistories=statusHistoryRepository.findByJobApplicationIdOrderByChangedAtAsc(id);

        //convert to status history response and return back

        return statusHistories.stream().map(
                statusHistoryResponseAdapter::toResponse
        ).toList();

    }


}

