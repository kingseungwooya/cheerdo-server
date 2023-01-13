package com.example.cheerdo.post.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    @ApiModelProperty( example = "보낸사람 id")
    private final String senderId;

    @ApiModelProperty( example = "보낸사람 이름")
    private final String senderName;

    @ApiModelProperty( example = "letter 의 고유번호")
    private final Long letterId;

}
