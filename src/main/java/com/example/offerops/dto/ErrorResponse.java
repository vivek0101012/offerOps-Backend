package com.example.offerops.dto;


import com.example.offerops.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    final String message;
    final int status;
    private final ErrorCode code;
    final LocalDateTime timestamp;

}

