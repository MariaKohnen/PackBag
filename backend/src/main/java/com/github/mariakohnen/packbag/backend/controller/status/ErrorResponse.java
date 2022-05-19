package com.github.mariakohnen.packbag.backend.controller.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private HttpStatus error;
    private String errorMessage;
    private String errorCause;
    private LocalDateTime timestamp;
    private String requestUri;
}
