package com.example.cheerdo.todo.dto.request;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.todo.enums.Type;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WriteTodoRequestDto {

    private static final boolean DEFAULT_STATUS = false;
    private String userId;

    private String type;
    // time 프론트에서 받을건지 서버에서 해결할 것인지?
    // private LocalDateTime writeTime;
    // private LocalDateTime date;
    private String todo;

    public Todo requestToEntity(Member member) {
        return Todo.builder()
                .date(LocalDate.now())
                .content(todo)
                .isSuccess(DEFAULT_STATUS)
                .member(member)
                .type(Type.valueOf(type))
                .build();
    }
}
