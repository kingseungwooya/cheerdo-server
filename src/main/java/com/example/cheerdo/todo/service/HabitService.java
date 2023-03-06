package com.example.cheerdo.todo.service;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteHabitRequestDto;
import com.example.cheerdo.todo.dto.response.HabitInfoResponseDto;
import com.example.cheerdo.todo.dto.response.HabitResponseDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import java.util.*;

public interface HabitService {
    Long writeHabit(WriteHabitRequestDto writeHabitRequestDto);

    void deleteHabit(Long habitId);

    List<HabitResponseDto> getMyHabits(GetTodoRequestDto getTodoRequestDto);

    List<HabitInfoResponseDto> getHabitInfo(String memberId);
}
