package com.example.cheerdo.todo.dto.request;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WriteTodoRequestDto {

    private static final boolean DEFAULT_STATUS = false;
    private String todoId;
    private String userId;
    private String type;
     private LocalDateTime endDateTime;
    private String todo;

    public Todo requestToEntity(Member member) {
        return Todo.builder()
                .date(LocalDate.now())
                .content(todo)
                .isSuccess(DEFAULT_STATUS)
                .member(member)
                .type(Type.valueOf(type))
                .endDateTime(endDateTime)
                .todoId(todoId)
                .build();
    }
}
