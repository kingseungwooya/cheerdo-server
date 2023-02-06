package com.example.cheerdo.todo.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTodoRequestDto {
    private Long todoId;
    private String todo;

}
