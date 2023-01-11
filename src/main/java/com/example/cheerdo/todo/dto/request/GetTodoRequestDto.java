package com.example.cheerdo.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GetTodoRequestDto {
    private String userId;
    private LocalDateTime searchDate;
}
