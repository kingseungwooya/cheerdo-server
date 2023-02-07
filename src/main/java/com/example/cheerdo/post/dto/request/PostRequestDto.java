package com.example.cheerdo.post.dto.request;

import com.example.cheerdo.common.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PostRequestDto {
    private boolean open;
    private String memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SortType sortType;
}
