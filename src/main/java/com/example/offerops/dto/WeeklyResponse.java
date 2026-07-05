package com.example.offerops.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyResponse {
    private String week;
    private Long count;

}
