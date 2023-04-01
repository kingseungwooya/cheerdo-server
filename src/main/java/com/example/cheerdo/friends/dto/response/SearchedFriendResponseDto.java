package com.example.cheerdo.friends.dto.response;

import lombok.Builder;
import lombok.Getter;


@Getter
public class SearchedFriendResponseDto {
    private final String name;
    private final String memberId;
    private final String image;

    @Builder
    public SearchedFriendResponseDto(String name, String memberId, String image) {
        this.name = name;
        this.memberId = memberId;
        this.image = image;
    }
}
