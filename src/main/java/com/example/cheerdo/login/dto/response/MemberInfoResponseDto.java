package com.example.cheerdo.login.dto.response;

import lombok.Data;

@Data
public class MemberInfoResponseDto {
    private final String memberId;
    private final String name;
    private final String bio;
    private final int coinCount;
    private final double habitProgress;

}
