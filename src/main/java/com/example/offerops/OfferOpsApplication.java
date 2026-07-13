package com.example.offerops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OfferOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfferOpsApplication.class, args);
    }

}