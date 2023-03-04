package com.example.cheerdo.todo.dto.request;

import com.example.cheerdo.entity.Habit;
import com.example.cheerdo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WriteHabitRequestDto {
    private String memberId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String content;

    public Habit requestTodHabit(Member member) {
        return Habit.builder()
                .endDate(endDate)
                .startDate(startDate)
                .content(content)
                .member(member)
                .build();
    }
}
