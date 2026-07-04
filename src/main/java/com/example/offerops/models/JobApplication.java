package com.example.offerops.models;

import com.example.offerops.constant.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobApplication extends  BaseModel{



    @ManyToOne
    @JoinColumn(name = "user_id" ,nullable = false)
    private  User user;


    @OneToMany(
            mappedBy = "jobApplication",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<StatusHistory> statusHistory;


    @Column(nullable = false)
    String  company;

    @Column(nullable = false)
    String role;

    String jdUrl;

    @Column(columnDefinition = "TEXT")
    String jdDescription;

    String resumeUrl;

    @Column(columnDefinition = "varchar(50) default 'DIRECT'")
    String source;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'APPLIED'" )
    ApplicationStatus status;

    @Column(nullable = false)
    LocalDate appliedDate;

    LocalDate deadlineDate;

    @Column(columnDefinition = "Text" )
    String notes;

    @PrePersist public void setDefaults() {
        if (this.status == null) this.status = ApplicationStatus.APPLIED;
        if (this.source == null) this.source = "DIRECT";

    }

}
