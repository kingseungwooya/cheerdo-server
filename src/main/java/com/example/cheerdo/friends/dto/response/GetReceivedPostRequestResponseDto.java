package com.example.cheerdo.friends.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class GetReceivedPostRequestResponseDto {
    private LocalDateTime sendDateTime;
    private String friendId;
    private String friendName;
    private String memberImage;
    private Long relationId;
}
