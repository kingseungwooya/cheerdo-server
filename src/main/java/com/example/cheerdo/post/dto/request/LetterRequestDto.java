package com.example.cheerdo.post.dto.request;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Post;
import com.example.cheerdo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LetterRequestDto {
    private Long relationId;

    private String title;

    private String message;

    private String todoId;


    public Post dtoToPostEntity(FriendRelation relation, Todo todo) {
        return Post.builder()
                .title(title)
                .message(message)
                .sendDateTime(LocalDate.now())
                .senderName(relation.getMember().getName())
                .receiverId(relation.getFriendId())
                .relation(relation)
                .todo(todo)
                .build();
    }
}
