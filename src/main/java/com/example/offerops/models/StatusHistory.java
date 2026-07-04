package com.example.offerops.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StatusHistory  extends  BaseModel {



    @ManyToOne
    @JoinColumn(name = "app_id",nullable = false)
    private  JobApplication jobApplication;

    private  String notes;

    @Column(nullable = false)
    private  String fromStatus;

    @Column(nullable = false)
    private  String toStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime changedAt;

}
