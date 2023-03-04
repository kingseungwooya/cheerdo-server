package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.WriteHabitRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.service.HabitService;
import com.example.cheerdo.todo.service.TodoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/member/habit")
public class HabitController {
    private final HabitService habitService;
    private final Logger logger = LoggerFactory.getLogger(HabitController.class);

    @ApiOperation(value = "Habit를 생성하는 api"
            , notes = "반환값으로 Todo의 id가 반환된다. ")
    @PostMapping("")
    public ResponseEntity<Long> writeHabit(@RequestBody WriteHabitRequestDto writeHabitRequestDto) {
        return new ResponseEntity<>(habitService.writeHabit(writeHabitRequestDto), HttpStatus.CREATED);
    }
}
