package com.example.offerops.controllers;


import com.example.offerops.dto.*;
import com.example.offerops.services.ApplicationServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/applications")

@RestController
public class ApplicationController {

    Long userId=1L;


    private  final ApplicationServices applicationServices;

    public ApplicationController(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody @Valid ApplicationRequest request){

        ApplicationResponse response= applicationServices.createApplication(
                request,userId
        );
        return  ResponseEntity.ok(response);
        //how to get the id
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications(){

        List<ApplicationResponse> response= applicationServices.getAllApplications(userId);

        return  ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public  ResponseEntity<ApplicationResponse> getApplication(
            @PathVariable Long id
    ){
        ApplicationResponse response = applicationServices.getApplicationById(id,userId);

        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @PathVariable Long id,
            @RequestBody UpdateApplicationRequest request
            ){

        ApplicationResponse response= applicationServices.updateApplication(id,userId,request);
        return  ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public  ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStatusRequest request
    ){
        ApplicationResponse response = applicationServices.updateStatus(id,userId,request);

        return  ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        Long userId = 1L;
        applicationServices.deleteApplication(id, userId);
        return ResponseEntity.ok(new MessageResponse("Application deleted successfully"));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<StatusHistoryResponse>> getStatusHistory(@PathVariable Long id){

        List<StatusHistoryResponse> responses=  applicationServices.getStatusHistory(id,userId);

        return  ResponseEntity.ok(responses);

    }
}
