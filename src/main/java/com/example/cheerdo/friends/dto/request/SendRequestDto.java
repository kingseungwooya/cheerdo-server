package com.example.cheerdo.friends.dto.request;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class SendRequestDto {
    String memberId;
    String friendId;

    public FriendRelation dtoToFriendRelationEntity(Optional<Member> member) {
        return FriendRelation.builder()
                .friendId(this.friendId)
                .member(member.get())
                .isFriend(true)
                .build();
    }
}
