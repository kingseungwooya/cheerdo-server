package com.example.cheerdo.todo.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
public class HabitInfoResponseDto {
    @ApiModelProperty(example = "Habit의 고유번호")
    private final Long habitId;

    @ApiModelProperty(example = "Habit의 내용")
    private final String content;

    @ApiModelProperty(example = "Habit의 성공Rate")
    private final double successRate;

    @ApiModelProperty(example = "Habit의 시작날짜")
    private final LocalDate startDate;

    @ApiModelProperty(example = "Habit의 종료날짜")
    private final LocalDate endDate;

    @Builder
    public HabitInfoResponseDto(Long habitId, String content, double successRate, LocalDate startDate,
                                LocalDate endDate) {
        this.habitId = habitId;
        this.content = content;
        this.successRate = successRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
