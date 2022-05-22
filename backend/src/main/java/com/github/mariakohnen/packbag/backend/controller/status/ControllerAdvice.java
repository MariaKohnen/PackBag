package com.github.mariakohnen.packbag.backend.controller.status;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException exception, WebRequest request) {

        ErrorResponse bodyOfResponse = ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST)
                .errorMessage(": There was an illegal argument in the request.")
                .errorCause(exception.toString())
                .timestamp(LocalDateTime.now())
                .requestUri(request.getDescription(false))
                .build();

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
