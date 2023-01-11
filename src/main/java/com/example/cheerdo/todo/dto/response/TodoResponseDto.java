package com.example.cheerdo.todo.dto.response;

import com.example.cheerdo.todo.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class TodoResponseDto {
    private final Long todoId;

    private final Type typeOfTodo;
    private final String todo;
}
