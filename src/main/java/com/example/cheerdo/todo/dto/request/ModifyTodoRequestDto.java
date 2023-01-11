package com.example.cheerdo.todo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ModifyTodoRequestDto {
    private Long todoId;
    private String todo;

}
