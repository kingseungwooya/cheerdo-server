package com.example.cheerdo.post.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class LetterResponseDto {

    @ApiModelProperty("letter의 고유번호")
    private final Long letterId;

    @ApiModelProperty("보낸사람 이름")
    private final String senderName;

    @ApiModelProperty("편지제목")
    private final String title;

    @ApiModelProperty("편지내용")
    private final String message;

    @ApiModelProperty("관계 교유키")
    private final Long relationId;

    @ApiModelProperty("편지 보낸날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate sendDate;

    @ApiModelProperty("편지를 보낸 Todo의 id")
    private final String todoId;

    @ApiModelProperty("편지를 보낸 Todo의 content")
    private final String todoContent;

    @ApiModelProperty("편지를 보낸 Todo의 date")
    private final LocalDate todoDate;

    @Builder
    public LetterResponseDto(Long letterId, String senderName, String title, String message, Long relationId,
                             LocalDate sendDate, String todoId, String todoContent, LocalDate todoDate) {
        this.letterId = letterId;
        this.senderName = senderName;
        this.title = title;
        this.message = message;
        this.relationId = relationId;
        this.sendDate = sendDate;
        this.todoId = todoId;
        this.todoContent = todoContent;
        this.todoDate = todoDate;
    }
}
