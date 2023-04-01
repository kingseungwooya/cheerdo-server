package com.example.cheerdo.friends.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SentFollowResponseDto {
    private Long relationId;
    private String name;
}
