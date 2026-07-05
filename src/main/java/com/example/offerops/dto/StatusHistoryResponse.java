package com.example.offerops.dto;

import com.example.offerops.constant.ApplicationStatus;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistoryResponse {

    private Long id;
    private ApplicationStatus fromStatus;
    private ApplicationStatus toStatus;
    private LocalDateTime changedAt;
    private  String notes;

}