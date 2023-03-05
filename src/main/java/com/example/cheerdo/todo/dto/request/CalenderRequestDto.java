package com.example.cheerdo.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;

@Getter
@AllArgsConstructor
public class CalenderRequestDto {
    private String memberId;
    private String searchYearMonth;
}
