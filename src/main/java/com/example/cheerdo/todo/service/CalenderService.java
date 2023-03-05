package com.example.cheerdo.todo.service;

import com.example.cheerdo.todo.dto.request.CalenderRequestDto;
import com.example.cheerdo.todo.dto.response.CalenderResponseDto;

import java.util.*;

public interface CalenderService {
    List<CalenderResponseDto> getSuccessRates(CalenderRequestDto calenderRequestDto);
}
