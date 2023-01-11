package com.example.cheerdo.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class WriteTodoRequestDto {
    private String userId;
    // time 프론트에서 받을건지 서버에서 해결할 것인지?
    // private LocalDateTime writeTime;
    // private LocalDateTime date;
    private String todo;
}
