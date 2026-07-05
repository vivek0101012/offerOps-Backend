package com.example.offerops.dto;


import lombok.*;

import java.security.PrivateKey;

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

