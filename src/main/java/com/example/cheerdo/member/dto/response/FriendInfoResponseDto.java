package com.example.cheerdo.member.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendInfoResponseDto {
    private final String memberId;
    private final String name;
    private final String bio;
    private final String image;
    private final int dPlusCount;
    @ApiModelProperty("이 친구가 나한테 보낸 편지 개수 ")
    private final long getLetterCount;

    @ApiModelProperty("이 친구한테 내가 보낸 편지 개수 ")
    private final long sendLetterCount;

    @Builder
    public FriendInfoResponseDto(String memberId, String name, String bio, String image, int dPlusCount,
                                 long getLetterCount, long sendLetterCount) {
        this.memberId = memberId;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.dPlusCount = dPlusCount;
        this.getLetterCount = getLetterCount;
        this.sendLetterCount = sendLetterCount;
    }
}
