package com.example.cheerdo.todo.dto.request;

import com.example.cheerdo.todo.enums.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class GetTodoRequestDto {
    private String userId;

    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchDate;
}
