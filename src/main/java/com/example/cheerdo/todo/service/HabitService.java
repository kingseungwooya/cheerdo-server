package com.example.cheerdo.todo.service;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteHabitRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import java.util.*;

public interface HabitService {
    Long writeHabit(WriteHabitRequestDto writeHabitRequestDto);

    void deleteHabit(Long habitId);

    List<TodoResponseDto> getMyHabits(GetTodoRequestDto getTodoRequestDto);
}
