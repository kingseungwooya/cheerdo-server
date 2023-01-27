package com.example.cheerdo.friendList.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class LoadFriendResponseDto {

    private Long relationId;
    private String name;
    private String memberId;

}
