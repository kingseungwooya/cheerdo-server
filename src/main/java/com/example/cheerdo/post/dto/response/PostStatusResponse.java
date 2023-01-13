package com.example.cheerdo.post.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostStatusResponse {

    @ApiModelProperty("잔여 코인개수")
    private final long coinCount;
    @ApiModelProperty("읽지않은 편지 수 ")
    private final long unreadLetterCount;

}
