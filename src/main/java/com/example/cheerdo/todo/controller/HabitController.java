package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteHabitRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.HabitInfoResponseDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.todo.service.HabitService;
import com.example.cheerdo.todo.service.TodoService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation(value = "Todo를 delete하는 api"
            , notes = "반환값이 존재하지 않는다 error 시 error message 반환")
    @DeleteMapping("")
    public ResponseEntity<?> deleteHabit(@RequestParam Long habitId) {
        try {
            habitService.deleteHabit(habitId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Todo를 Get하는 api"
            , notes = "todo list들이 반환된다. ")
    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> getMyHabit(@ModelAttribute GetTodoRequestDto getTodoRequestDto) {
        return new ResponseEntity<>(habitService.getMyHabits(getTodoRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Todo를 Get하는 api"
            , notes = "todo list들이 반환된다. ")
    @GetMapping("/progress")
    public ResponseEntity<List<HabitInfoResponseDto>> getMyHabitProgress(@RequestParam String memberId) {
        return new ResponseEntity<>(habitService.getHabitInfo(memberId), HttpStatus.OK);
    }
}
