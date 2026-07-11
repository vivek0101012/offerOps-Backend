package com.example.offerops.controllers;


import com.example.offerops.dto.*;
import com.example.offerops.security.CustomUserDetails;
import com.example.offerops.services.ApplicationServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/applications")

@RestController
public class ApplicationController {




    private  final ApplicationServices applicationServices;

    public ApplicationController(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@AuthenticationPrincipal
                                                                 CustomUserDetails user,@RequestBody @Valid ApplicationRequest request){

        Long userId = user.getId();
        ApplicationResponse response= applicationServices.createApplication(
                request,userId
        );
        return  ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications(@AuthenticationPrincipal CustomUserDetails user){
        Long userId = user.getId();
        List<ApplicationResponse> response= applicationServices.getAllApplications(userId);

        return  ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public  ResponseEntity<ApplicationResponse> getApplication(  @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long id
    ){
        Long userId = user.getId();
        ApplicationResponse response = applicationServices.getApplicationById(id,userId);

        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id,
            @RequestBody @Valid  UpdateApplicationRequest request
            ){
        Long userId = user.getId();
        ApplicationResponse response= applicationServices.updateApplication(id,userId,request);
        return  ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public  ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id,
            @RequestBody @Valid UpdateStatusRequest request
    ){
        Long userId = user.getId();
        ApplicationResponse response = applicationServices.updateStatus(id,userId,request);

        return  ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@AuthenticationPrincipal CustomUserDetails user,
                                                  @PathVariable Long id) {
        Long userId = user.getId();
        applicationServices.deleteApplication(userId, id);
        return ResponseEntity.ok(new MessageResponse("Application deleted successfully"));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<StatusHistoryResponse>> getStatusHistory(@AuthenticationPrincipal CustomUserDetails user,@PathVariable Long id){

        Long userId = user.getId();
        List<StatusHistoryResponse> responses=  applicationServices.getStatusHistory(id,userId);

        return  ResponseEntity.ok(responses);

    }
}
