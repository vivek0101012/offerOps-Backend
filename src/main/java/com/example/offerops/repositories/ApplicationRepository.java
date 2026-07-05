package com.example.offerops.repositories;

import com.example.offerops.constant.ApplicationStatus;
import com.example.offerops.models.JobApplication;
import com.example.offerops.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends  JpaRepository<JobApplication,Long> {


    List<JobApplication> findByUserId(Long userId);

    Optional<JobApplication> findByIdAndUserId(Long id, Long userId);

    List<JobApplication> findByUserIdAndDeadlineDateBetween(
            Long user_id, LocalDate deadlineDate, LocalDate deadlineDate2);

    List<JobApplication> findByUserIdAndStatus(
            Long userId, ApplicationStatus status);


}
