package com.example.offerops.state;

import com.example.offerops.constant.ApplicationStatus;

public interface ApplicationState {

    void  validate( ApplicationStatus requestedstate);
}
