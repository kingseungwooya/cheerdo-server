package com.example.cheerdo.entity;

import com.example.cheerdo.todo.dto.response.HabitInfoResponseDto;
import java.time.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder
    public Habit(Long id, Member member, String content, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public HabitInfoResponseDto entityToHabitInfoDto() {
        return HabitInfoResponseDto.builder()
                .habitId(id)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .dPlusDay(getDDay())
                .build();
    }

    public int getDDay() {
        int duration = (int) Duration.between(startDate, LocalDate.now()).toDays();
        return duration;
    }
}
