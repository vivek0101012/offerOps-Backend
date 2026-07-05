package com.example.offerops.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceResponse {
    private String source;
    private Long count;
}
