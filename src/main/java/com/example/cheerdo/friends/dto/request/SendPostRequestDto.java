package com.example.cheerdo.friends.dto.request;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.PostRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class SendPostRequestDto {
    Long relationId;

    public PostRequest dtoToPostRequestEntity(FriendRelation friendRelation) {
        return PostRequest.builder()
                .friendRelation(friendRelation)
                .sendDateTime(LocalDateTime.now())
                .build();
    }
}
