package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.github.javafaker.Faker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main/todo")
public class TodoController {

    private final Faker faker = new Faker();

    @PostMapping("/")
    public ResponseEntity<Long> writeTodo(@RequestBody WriteTodoRequestDto todoDto) {
        Long todoId = faker.number().randomNumber();
        return new ResponseEntity<>(todoId, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<TodoResponseDto>> getMyTodo(@RequestBody GetTodoRequestDto getTodoRequestDto) {
        List<TodoResponseDto> responseDtos = List.of(
                new TodoResponseDto(faker.number().randomNumber(), faker.lorem().sentence(5)),
                new TodoResponseDto(faker.number().randomNumber(), faker.lorem().sentence(5)),
                new TodoResponseDto(faker.number().randomNumber(), faker.lorem().sentence(5))
        );
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Long> modifyTodo (@RequestBody ModifyTodoRequestDto modifyTodoRequestDto) {
        Long todoId = modifyTodoRequestDto.getTodoId();
        return new ResponseEntity<>(todoId, HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodo (@PathVariable("todoId") Long todoId) {
        
    }


}
