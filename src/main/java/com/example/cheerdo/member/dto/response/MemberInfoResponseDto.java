package com.example.cheerdo.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponseDto {
    private final String memberId;
    private final String name;
    private final String bio;
    private final String image;
    private final int coinCount;
    private final int dPlusCount;

    @Builder
    public MemberInfoResponseDto(String memberId, String name, String bio, String image, int coinCount,
                                 int dPlusCount) {
        this.memberId = memberId;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.coinCount = coinCount;
        this.dPlusCount = dPlusCount;
    }
}
