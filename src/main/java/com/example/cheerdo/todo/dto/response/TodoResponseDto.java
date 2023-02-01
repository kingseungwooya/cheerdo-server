package com.example.cheerdo.todo.dto.response;

import com.example.cheerdo.todo.enums.Type;
import lombok.*;

@Data
public class TodoResponseDto {
    private final Long todoId;

    private final String typeOfTodo;
    private final String todo;

    @Builder
    public TodoResponseDto(Long todoId, String typeOfTodo, String todo) {
        this.todoId = todoId;
        this.typeOfTodo = typeOfTodo;
        this.todo = todo;
    }
}
