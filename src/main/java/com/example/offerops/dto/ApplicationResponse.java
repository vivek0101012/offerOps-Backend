package com.example.offerops.dto;

import com.example.offerops.constant.ApplicationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String company;
    private String role;
    private String jdUrl;
    private String jdDescription;
    private String resumeUrl;
    private String source;
    private ApplicationStatus status;
    private LocalDate appliedDate;
    private LocalDate deadlineDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}