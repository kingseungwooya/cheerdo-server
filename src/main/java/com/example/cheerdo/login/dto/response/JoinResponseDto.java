package com.example.cheerdo.login.dto.response;

import com.example.cheerdo.login.dto.response.error.ErrorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinResponseDto {
    private String id;

    private String bio;

    private int coinCount;

    private double habitProgress;
}
