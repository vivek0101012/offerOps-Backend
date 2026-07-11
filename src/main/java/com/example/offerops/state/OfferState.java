package com.example.offerops.state;

import com.example.offerops.constant.ApplicationStatus;
import com.example.offerops.exception.InvalidTransitionException;

public class OfferState implements ApplicationState {

    @Override
    public void validate(ApplicationStatus requestedStatus) {

        throw new InvalidTransitionException(
                "OFFER is a terminal state.");
    }
}