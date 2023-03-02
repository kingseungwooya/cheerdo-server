package com.example.cheerdo.todo.dto.request;

import com.example.cheerdo.entity.Calender;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.entity.enums.Type;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WriteTodoRequestDto {

    private static final boolean DEFAULT_STATUS = false;
    private String todoId;
    private String memberId;
    private String type;
    private LocalDateTime endDateTime;
    private String todo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Todo requestToEntity(Calender calender) {
        return Todo.builder()
                .content(todo)
                .isSuccess(DEFAULT_STATUS)
                .type(Type.valueOf(type))
                .endDateTime(endDateTime)
                .todoId(todoId)
                .calender(calender)
                .build();
    }
}
