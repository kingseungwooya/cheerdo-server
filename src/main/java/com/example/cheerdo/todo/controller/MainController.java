package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.TodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @PostMapping("/todo")
    public ResponseEntity<TodoResponseDto> writeTodo(@RequestBody TodoRequestDto todoDto) {
        Long todoId = 1L;
        TodoResponseDto todoResponseDto = new TodoResponseDto(todoId);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }
}
