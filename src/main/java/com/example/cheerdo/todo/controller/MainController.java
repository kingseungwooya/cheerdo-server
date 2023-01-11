package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @PostMapping("/todo")
    public ResponseEntity<Long> writeTodo(@RequestBody WriteTodoRequestDto todoDto) {
        Long todoId = 1L;
        return new ResponseEntity<>(todoId, HttpStatus.OK);
    }
    
}
