package com.example.cheerdo.post.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class PostResponseDto {

    @ApiModelProperty( example = "보낸사람 id")
    private final String senderId;

    @ApiModelProperty( example = "보낸사람 이름")
    private final String senderName;

    @ApiModelProperty( example = "letter 의 고유번호")
    private final Long letterId;

    @ApiModelProperty("관계 교유키")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate sendDate;

    @Builder
    public PostResponseDto(String senderId, String senderName, Long letterId, LocalDate sendDate) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.letterId = letterId;
        this.sendDate = sendDate;
    }
}
