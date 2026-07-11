package com.example.offerops.models;

import com.example.offerops.constant.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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


    @Enumerated(EnumType.STRING)
    private ApplicationStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  ApplicationStatus toStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime changedAt;

    @Column(columnDefinition = "Text" )
    private String notes;

}
