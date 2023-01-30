package com.example.cheerdo.todo.controller;

import com.example.cheerdo.todo.dto.request.GetTodoRequestDto;
import com.example.cheerdo.todo.dto.request.ModifyTodoRequestDto;
import com.example.cheerdo.todo.dto.request.WriteTodoRequestDto;
import com.example.cheerdo.todo.dto.response.TodoResponseDto;
import com.example.cheerdo.todo.enums.Type;
import com.github.javafaker.Faker;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main/todo")
public class TodoController {

    private final Faker faker = new Faker();
    private final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @ApiOperation(value = "Todo를 생성하는 api"
            , notes = "반환값으로 Httpstatus와 body에는 Long타입의 todoId( todo 고유번호 ) 가 반환된다.")
    @PostMapping("")
    public ResponseEntity<Long> writeTodo(@RequestBody WriteTodoRequestDto todoDto) {
        Long todoId = faker.number().randomNumber();
        return new ResponseEntity<>(todoId, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Todo를 Get하는 api"
            , notes = "반환값으로 Httpstatus와 body에는 todo list들이 반환된다. ")
    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> getMyTodo(@ModelAttribute GetTodoRequestDto getTodoRequestDto) {

        if (getTodoRequestDto.getType().equals(Type.TODO.name())) {
            logger.info("123");
            return new ResponseEntity<>(
                    List.of(
                            new TodoResponseDto(faker.number().randomNumber(), Type.TODO, faker.lorem().sentence(5)),
                            new TodoResponseDto(faker.number().randomNumber(), Type.TODO, faker.lorem().sentence(5)),
                            new TodoResponseDto(faker.number().randomNumber(), Type.TODO, faker.lorem().sentence(5))
                    ), HttpStatus.OK);
        }
        List<TodoResponseDto> list = List.of(
                new TodoResponseDto(faker.number().randomNumber(), Type.HABIT, faker.lorem().sentence(5)),
                new TodoResponseDto(faker.number().randomNumber(), Type.HABIT, faker.lorem().sentence(5)),
                new TodoResponseDto(faker.number().randomNumber(), Type.HABIT, faker.lorem().sentence(5)));
        logger.info(list.toString());
        return new ResponseEntity<>(
                List.of(
                        new TodoResponseDto(faker.number().randomNumber(), Type.HABIT, faker.lorem().sentence(5)),
                        new TodoResponseDto(faker.number().randomNumber(), Type.HABIT, faker.lorem().sentence(5)),
                        new TodoResponseDto(faker.number().randomNumber(), Type.HABIT, faker.lorem().sentence(5)))
                , HttpStatus.OK);

    }

    @ApiOperation(value = "Todo를 put(modify)하는 api"
            , notes = "반환값으로 Httpstatus와 body에는 Long타입의 todoId( todo 고유번호 ) 가 반환된다.")
    @PutMapping("")
    public ResponseEntity<Long> modifyTodo(@RequestBody ModifyTodoRequestDto modifyTodoRequestDto) {
        Long todoId = modifyTodoRequestDto.getTodoId();
        return new ResponseEntity<>(todoId, HttpStatus.OK);
    }

    @ApiOperation(value = "Todo를 delete하는 api"
            , notes = "반환값이 없는 void")
    @DeleteMapping("")
    public void deleteTodo(@RequestParam Long todoId) {

    }


}
