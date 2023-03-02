package com.example.cheerdo.todo.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GetTodoRequestDto {
    private String memberId;

    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchDate;
}
