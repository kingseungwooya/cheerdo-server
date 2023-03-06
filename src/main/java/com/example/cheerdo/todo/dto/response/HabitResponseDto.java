package com.example.cheerdo.todo.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class HabitResponseDto {
    @ApiModelProperty(example = "Habit의 고유번호")
    private final Long habitId;

    @ApiModelProperty(example = "Habit의 고유번호")
    private final String todoId;

    @ApiModelProperty(example = "todo의 타입 ( TODO or HABIT")
    private final String typeOfTodo;

    @ApiModelProperty(example = "todo의 내용")
    private final String content;

    @ApiModelProperty(example = "todo의 성공여부")
    private final boolean success;

    @Builder
    public HabitResponseDto(Long habitId, String todoId, String typeOfTodo, String content, boolean success) {
        this.habitId = habitId;
        this.todoId = todoId;
        this.typeOfTodo = typeOfTodo;
        this.content = content;
        this.success = success;
    }
}
