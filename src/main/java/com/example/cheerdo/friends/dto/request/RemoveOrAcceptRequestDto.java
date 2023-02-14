package com.example.cheerdo.friends.dto.request;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class RemoveOrAcceptRequestDto {
    private Long relationId;
    private boolean accept;

    public FriendRelation dtoToFriendRelationEntity(Optional<Member> member, String friendId) {
        return FriendRelation.builder()
                .friendId(friendId)
                .member(member.get())
                .isFriend(true)
                .hasMessage(false)
                .build();
    }
}
