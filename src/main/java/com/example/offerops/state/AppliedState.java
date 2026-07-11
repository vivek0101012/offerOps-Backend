package com.example.offerops.state;

import com.example.offerops.constant.ApplicationStatus;
import com.example.offerops.exception.InvalidTransitionException;

public class AppliedState implements ApplicationState{
    @Override
    public void validate(ApplicationStatus requestedStatus) {


        if (requestedStatus == ApplicationStatus.OA
                || requestedStatus == ApplicationStatus.REJECTED
                || requestedStatus == ApplicationStatus.INTERVIEW_R1
        ) {
            return;
        }

        throw new InvalidTransitionException(
                "Cannot transition from APPLIED to " + requestedStatus);

    }
}
