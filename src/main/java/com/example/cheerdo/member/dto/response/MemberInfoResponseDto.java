package com.example.cheerdo.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponseDto {
    private final String memberId;
    private final String name;
    private final String bio;
    private final int coinCount;
    private final double habitProgress;
    private final Long dPlusCount;

    @Builder
    public MemberInfoResponseDto(String memberId, String name, String bio, int coinCount, double habitProgress, Long dPlusCount) {
        this.memberId = memberId;
        this.name = name;
        this.bio = bio;
        this.coinCount = coinCount;
        this.habitProgress = habitProgress;
        this.dPlusCount = dPlusCount;
    }
}
