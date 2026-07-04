package com.example.offerops.exception;


import com.example.offerops.constant.ErrorCode;
import com.example.offerops.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        ErrorResponse response= ErrorResponse.builder().code(ErrorCode.USER_NOT_FOUND)
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public  ResponseEntity<ErrorResponse>handleUserAlreayExist(UserAlreadyExist ex){
        ErrorResponse response= ErrorResponse.builder()
                .code(ErrorCode.USER_ALREADY_EXISTS)
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value()).build();
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
