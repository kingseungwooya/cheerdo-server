package com.example.cheerdo.todo.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTodoRequestDto {
    private String todoId;
    private String todo;

}
