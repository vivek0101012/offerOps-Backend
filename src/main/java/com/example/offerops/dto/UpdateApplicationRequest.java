package com.example.offerops.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationRequest {

    private String company;

    private String role;

    private String source;

    private LocalDate appliedDate;

    private String jdUrl;
    private String jdDescription;
    private String resumeUrl;
    private LocalDate deadlineDate;
    private String notes;

}
