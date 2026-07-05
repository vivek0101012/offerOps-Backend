package com.example.offerops.dto;

import com.example.offerops.constant.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateStatusRequest {

    @
            NotNull(message = "Status is required")
    private ApplicationStatus status;
}