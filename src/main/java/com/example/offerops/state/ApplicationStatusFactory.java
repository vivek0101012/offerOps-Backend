package com.example.offerops.state;

import com.example.offerops.constant.ApplicationStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ApplicationStatusFactory {


    public  ApplicationState get(ApplicationStatus status){


        return switch (status) {
            case APPLIED -> new AppliedState();
            case OA -> new OAState();
            case INTERVIEW_R1 -> new InterviewR1State();
            case INTERVIEW_R2 -> new InterviewR2State();
            case OFFER -> new OfferState();
            case REJECTED -> new RejectedState();
            default -> throw new IllegalArgumentException("Unknown application status: " + status);
        };
    }




}
