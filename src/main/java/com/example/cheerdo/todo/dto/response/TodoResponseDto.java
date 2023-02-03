package com.example.cheerdo.todo.dto.response;

import com.example.cheerdo.todo.enums.Type;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;

@Data
public class TodoResponseDto {
    @ApiModelProperty( example = "todo의 고유번호")
    private final Long todoId;

    @ApiModelProperty( example = "todo의 타입 ( TODO or HABIT")
    private final String typeOfTodo;

    @ApiModelProperty( example = "todo의 내용")
    private final String todo;

    @Builder
    public TodoResponseDto(Long todoId, String typeOfTodo, String todo) {
        this.todoId = todoId;
        this.typeOfTodo = typeOfTodo;
        this.todo = todo;
    }
}
