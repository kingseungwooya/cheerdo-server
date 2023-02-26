package com.example.cheerdo.login.dto.response.error;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private HttpStatus status;
    private String message;
}