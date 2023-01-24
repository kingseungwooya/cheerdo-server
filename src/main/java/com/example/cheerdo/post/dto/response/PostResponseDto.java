package com.example.cheerdo.post.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
public class PostResponseDto {

    @ApiModelProperty( example = "보낸사람 id")
    private final String senderId;

    @ApiModelProperty( example = "보낸사람 이름")
    private final String senderName;

    @ApiModelProperty( example = "letter 의 고유번호")
    private final Long letterId;

    @Builder
    public PostResponseDto(String senderId, String senderName, Long letterId) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.letterId = letterId;
    }
}
