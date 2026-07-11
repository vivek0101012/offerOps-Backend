package com.example.offerops.dto;


import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {

    private  Long applicationCount;
    private  Long activeCount;
    private  Long offerCount;
    private  Double offerPercentage;
}

