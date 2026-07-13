package com.example.offerops.services;


import com.example.offerops.models.JobApplication;
import com.example.offerops.models.User;
import com.example.offerops.repositories.ApplicationRepository;
import com.example.offerops.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailDigestService {

    private static final Logger log = LoggerFactory.getLogger(EmailDigestService.class);
    private static final String RESEND_API_URL = "https://api.resend.com/emails";

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${resend.from.address}")
    private String fromEmail;

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmailDigestService(UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Async
    public void sendWeeklyDigest() {
        List<User> usersList = userRepository.findAll();

        for (User user : usersList) {
            sendDigestToUser(user);
        }
    }


    private void sendDigestToUser(User user) {

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysfromToday = today.plusDays(7);
        List<JobApplication> jobApplicationList = applicationRepository.findByUserIdAndDeadlineDateBetween(user.getId(),
                today, sevenDaysfromToday);

        if (jobApplicationList.isEmpty()) {
            return;
        }

        String body = buildEmailBody(user.getName(), jobApplicationList);
        sendSimpleEmail(user.getEmail(), "weekly digest of upcoming job application", body);
    }

    private String buildEmailBody(String name, List<JobApplication> apps) {
        StringBuilder emailBody = new StringBuilder();

        emailBody.append("Hello ").append(name).append(",<br><br>");
        emailBody.append("Here is your weekly digest of upcoming job application deadlines:<br><br>");

        for (JobApplication app : apps) {
            emailBody.append("&bull; ")
                    .append(app.getCompany())
                    .append(" — ")
                    .append(app.getRole())
                    .append(" (Deadline: ")
                    .append(app.getDeadlineDate() != null ? app.getDeadlineDate() : "N/A")
                    .append(")<br>");
        }

        emailBody.append("<br>Good luck with your applications!<br><br>");
        emailBody.append("Best regards,<br>");
        emailBody.append("OfferOps Team");

        return emailBody.toString();
    }


    public void sendSimpleEmail(String to, String subject, String htmlBody) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("from", fromEmail);
            payload.put("to", List.of(to));
            payload.put("subject", subject);
            payload.put("html", htmlBody);

            String jsonBody = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(RESEND_API_URL))
                    .timeout(Duration.ofSeconds(15))
                    .header("Authorization", "Bearer " + resendApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                log.info("Digest email sent successfully to {}", to);
            } else {
                log.error("Failed to send digest email to {}. Status: {}, Body: {}",
                        to, response.statusCode(), response.body());
            }
        } catch (Exception e) {
            log.error("Exception while sending digest email to {}", to, e);
        }
    }
}