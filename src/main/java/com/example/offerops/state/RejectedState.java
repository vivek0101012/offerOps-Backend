package com.example.offerops.state;

import com.example.offerops.constant.ApplicationStatus;
import com.example.offerops.exception.InvalidTransitionException;

public class RejectedState implements ApplicationState {

    @Override
    public void validate(ApplicationStatus requestedStatus) {

        throw new InvalidTransitionException(
                "REJECTED is a terminal state.");
    }
}