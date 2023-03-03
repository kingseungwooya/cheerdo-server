package com.example.cheerdo.todo.controller;


import com.example.cheerdo.todo.dto.request.CalenderRequestDto;
import com.example.cheerdo.todo.dto.response.CalenderResponseDto;
import com.example.cheerdo.todo.service.CalenderService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/member/calender")
public class CalenderController {
    private final Logger logger = LoggerFactory.getLogger(CalenderController.class);
    private final CalenderService calenderService;

    @ApiOperation(value = "calender 일자별 진행도 반환하는 api"
            , notes = "date와 rate가 반환된다.  ")
    @GetMapping("")
    public ResponseEntity<List<CalenderResponseDto>> writeTodo(@ModelAttribute CalenderRequestDto calenderRequestDto) {
        return new ResponseEntity<>(calenderService.getSuccessRates(calenderRequestDto), HttpStatus.OK);
    }


}
