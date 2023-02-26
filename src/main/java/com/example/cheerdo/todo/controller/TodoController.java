package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.service.TodoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// TODO: 2023-02-02
/*
    에러 처리를 위해 너무 많은 try catch와 와일드카드가 사용되었다.
    Controller Advice와 Exception Handler로 refactoring하기
*/
@RestController
@AllArgsConstructor
@RequestMapping("/api/member/main/todo")
public class TodoController {

    private final TodoService todoService;
    private final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @ApiOperation(value = "Todo를 생성하는 api"
            , notes = "반환값으로 Httpstatus와 body에는 Long타입의 todoId( todo 고유번호 ) 가 반환된다.")
    @PostMapping("")
    public ResponseEntity<String> writeTodo(@RequestBody WriteTodoRequestDto todoDto) {
        return new ResponseEntity<>(todoService.writeTodo(todoDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Todo를 Get하는 api"
            , notes = "반환값으로 Httpstatus와 body에는 todo list들이 반환된다. ")
    @GetMapping("")
    public ResponseEntity<?> getMyTodo(@ModelAttribute GetTodoRequestDto getTodoRequestDto) {
        logger.info("input userID is -> {}",getTodoRequestDto.getUserId());
        try {
            return new ResponseEntity<>(todoService.getMyTodos(getTodoRequestDto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Todo를 put(modify)하는 api"
            , notes = "반환값으로 Httpstatus와 body에는 Long타입의 todoId( todo 고유번호 ) 가 반환된다.")
    @PutMapping("")
    public ResponseEntity<?> modifyTodo(@RequestBody ModifyTodoRequestDto modifyTodoRequestDto) {
        try {
            todoService.modifyTodo(modifyTodoRequestDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Todo를 delete하는 api")
    @DeleteMapping("")
    public ResponseEntity<?> deleteTodo(@RequestParam String todoId) {
        try {
            todoService.deleteTodo(todoId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Todo 성공체크시 요청되는 api"
            , notes = "반환값이 없는 void")
    @PutMapping("/success")
    public ResponseEntity<?> updateSuccess(@RequestParam String todoId) {
        try {
            todoService.success(todoId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
