package com.example.offerops.state;

import com.example.offerops.constant.ApplicationStatus;
import com.example.offerops.exception.InvalidTransitionException;

public class InterviewR2State implements ApplicationState {

    @Override
    public void validate(ApplicationStatus requestedStatus) {

        if (requestedStatus == ApplicationStatus.OFFER
                || requestedStatus == ApplicationStatus.REJECTED) {
            return;
        }

        throw new InvalidTransitionException(
                "Cannot transition from INTERVIEW_R2 to " + requestedStatus);
    }
}