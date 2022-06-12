package com.github.mariakohnen.packbag.backend.controller.status;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException exception, WebRequest request) {

        ErrorResponse bodyOfResponse = ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST)
                .errorMessage(exception.getMessage())
                .errorCause(exception.toString())
                .timestamp(LocalDateTime.now())
                .requestUri(request.getDescription(false))
                .build();

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<Object> handleConflict(NoSuchElementException exception, WebRequest request) {

        ErrorResponse bodyOfResponse = ErrorResponse.builder()
                .error(HttpStatus.NOT_FOUND)
                .errorMessage(exception.getMessage())
                .errorCause(exception.toString())
                .timestamp(LocalDateTime.now())
                .requestUri(request.getDescription(false))
                .build();

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {NameAlreadyExistException.class})
    protected ResponseEntity<Object> handleConflict(NameAlreadyExistException exception, WebRequest request) {

        ErrorResponse bodyOfResponse = ErrorResponse.builder()
                .error(HttpStatus.CONFLICT)
                .errorMessage(exception.getMessage())
                .errorCause(exception.toString())
                .timestamp(LocalDateTime.now())
                .requestUri(request.getDescription(false))
                .build();

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
