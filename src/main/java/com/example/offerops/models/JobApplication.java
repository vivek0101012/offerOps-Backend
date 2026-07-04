package com.example.offerops.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    String jd_url;

    @Column(nullable = false )
    String jd_description;

    @Column (nullable = false)
    String resume_url;

    @Column(columnDefinition = "varchar(255) default 'DIRECT'")
    String source;

    String status;

    @Column(nullable = false)
    Date appliedDate;

    @Column(nullable = false)
    Date deadlineDate;

    String notes;

}
