package com.example.cheerdo.friends.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
public class GetSearchedFriendResponseDto {
    private String name;
    private String memberId;

}
