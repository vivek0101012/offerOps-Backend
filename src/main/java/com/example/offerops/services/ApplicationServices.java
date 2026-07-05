package com.example.offerops.services;


import com.example.offerops.adapter.ApplicationResponseAdapter;
import com.example.offerops.adapter.CreateApplicationAdapter;
import com.example.offerops.adapter.UpdateApplicationAdapter;
import com.example.offerops.dto.ApplicationRequest;
import com.example.offerops.dto.ApplicationResponse;
import com.example.offerops.dto.UpdateApplicationRequest;
import com.example.offerops.exception.JobApplicationNotFound;
import com.example.offerops.models.JobApplication;
import com.example.offerops.models.User;
import com.example.offerops.repositories.ApplicationRepository;
import org.springframework.data.util.TypeCollector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServices  {


    private final ApplicationRepository applicationRepository;
    private final CreateApplicationAdapter createApplicationAdapter;
    private  final UpdateApplicationAdapter updateApplicationAdapter;
    private  final  ApplicationResponseAdapter applicationResponseAdapter;

    public ApplicationServices(
            ApplicationRepository applicationRepository,
            CreateApplicationAdapter createApplicationAdapter,
            UpdateApplicationAdapter updateApplicationAdapter,
            ApplicationResponseAdapter applicationResponseAdapter
    ) {
        this.applicationRepository = applicationRepository;
        this.createApplicationAdapter = createApplicationAdapter;
        this.updateApplicationAdapter = updateApplicationAdapter;
        this.applicationResponseAdapter = applicationResponseAdapter;
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

    // private — used internally by update, delete, updateStatus
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

        //finding application
        JobApplication jobApplication= applicationRepository.findByIdAndUserId(id,userId)
                .orElseThrow(()-> new JobApplicationNotFound("No such job application found"));

        jobApplication  = updateApplicationAdapter.toEntity(jobApplication,request);

        applicationRepository.save(jobApplication);

        return  applicationResponseAdapter.toResponse(jobApplication);

    }

    //update status we will do later


    //delete
    public  void   deleteApplication(Long userId,Long id){

        //user verifications based on token

        //find application based on user id
        JobApplication jobApplication= applicationRepository.findByIdAndUserId(id,userId).orElseThrow(
                ()->new JobApplicationNotFound("No Job Application found")
                );

        applicationRepository.delete(jobApplication);


    }


}

