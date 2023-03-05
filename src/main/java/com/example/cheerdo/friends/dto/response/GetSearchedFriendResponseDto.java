package com.example.cheerdo.friends.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;


@Getter
public class GetSearchedFriendResponseDto {
    private final String name;
    private final String memberId;
    private final String image;

    @Builder
    public GetSearchedFriendResponseDto(String name, String memberId, String image) {
        this.name = name;
        this.memberId = memberId;
        this.image = image;
    }
}
