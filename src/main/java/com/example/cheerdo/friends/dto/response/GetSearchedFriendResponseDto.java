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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetSearchedFriendResponseDto that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, memberId);
    }
}
