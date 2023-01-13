package com.example.cheerdo.post.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class letterResponseDto {
    @ApiModelProperty("letter의 고유번호")
    private final Long letterId;
    @ApiModelProperty("보낸사람 Id")
    private final String senderId;
    @ApiModelProperty("보낸사람 이름")
    private final String senderName;
    @ApiModelProperty("편지제목")
    private final String title;
    @ApiModelProperty("편지내용")
    private final String message;
    // 열람되었는지 확인하는 변수 이건 entity에 추가하는게 맞을 듯 싶다.
    // private boolean isOpened;
}
