package com.example.offerops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Source is required")
    private String source;

    @NotNull(message = "Applied date is required")
    private LocalDate appliedDate;

    private String jdUrl;
    private String jdDescription;
    private String resumeUrl;
    private LocalDate deadlineDate;
    private String notes;
}
