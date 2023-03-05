package com.example.cheerdo.todo.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CalenderResponseDto {

    @ApiModelProperty(example = "각 날짜별 todo의 성공 rate를 반환한다. ex) 83.5")
    private double successRate;

    @ApiModelProperty(example = "날짜 반환 yyyy-MM-dd")
    private LocalDate date;

    @Builder
    public CalenderResponseDto(double successRate, LocalDate date) {
        this.successRate = successRate;
        this.date = date;
    }
}
