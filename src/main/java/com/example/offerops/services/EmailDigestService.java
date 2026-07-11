package com.example.offerops.services;


import com.example.offerops.models.JobApplication;
import com.example.offerops.models.User;
import com.example.offerops.repositories.ApplicationRepository;
import com.example.offerops.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailDigestService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    private  final UserRepository userRepository;
    private  final ApplicationRepository applicationRepository;

    private final  JavaMailSender mailSender;

    public EmailDigestService(UserRepository userRepository, ApplicationRepository applicationRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.mailSender = mailSender;
    }

    public void sendWeeklyDigest() {
        List<User> usersList = userRepository.findAll();



        for (User user : usersList) {
            sendDigestToUser(user);
        }

    }


     private void sendDigestToUser(User user){

        LocalDate today=LocalDate.now();
        LocalDate sevenDaysfromToday=today.plusDays(7);
        List<JobApplication> jobApplicationList=applicationRepository.findByUserIdAndDeadlineDateBetween(user.getId(),
                                                        today,sevenDaysfromToday);

        if (jobApplicationList.isEmpty()){
            return;
        }

        String body=buildEmailBody(user.getName(),jobApplicationList);
        sendSimpleEmail(user.getEmail(),"weekly digest of upcoming job application",body);
     }

    private String buildEmailBody(String name, List<JobApplication> apps) {
        StringBuilder emailBody = new StringBuilder();

        emailBody.append("Hello ").append(name).append(",\n\n");
        emailBody.append("Here is your weekly digest of upcoming job application deadlines:\n\n");

        for (JobApplication app : apps) {
            emailBody.append("• ")
                    .append(app.getCompany())
                    .append(" — ")
                    .append(app.getRole())
                    .append(" (Deadline: ")
                    .append(app.getDeadlineDate() != null ? app.getDeadlineDate() : "N/A")
                    .append(")\n");
        }

        emailBody.append("\nGood luck with your applications!\n\n");
        emailBody.append("Best regards,\n");
        emailBody.append("OfferOps Team");

        return emailBody.toString();
    }



    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);

        mailSender.send(message);
    }
}
