package com.example.cheerdo.friends.dto.request;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendRequestDto {
    String memberId;
    String friendId;

    public FriendRelation dtoToFriendRelationEntity(Member member) {
        return FriendRelation.builder()
                .friendId(friendId)
                .member(member)
                .isFriend(false)
                .build();
    }
}
