package com.example.cheerdo.friends.controller;

import com.example.cheerdo.friends.dto.response.PostRequestResponseDto;
import com.example.cheerdo.friends.service.PostRequestService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/post-request")
@RequiredArgsConstructor
public class PostRequestController {

    private final PostRequestService postRequestService;

    @ApiOperation(value = "친구에게 편지요청을 할 수 있다.")
    @PostMapping(value = "/")
    public ResponseEntity<?> sendPostRequest(@RequestParam Long relationId) {
        postRequestService.sendPostRequest(relationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "받은 편지요청을 반환한다.")
    @GetMapping(value = "/")
    public ResponseEntity<?> getReceivedPostRequest(@RequestParam String memberId) {
        return new ResponseEntity<>(postRequestService.getReceivedPostRequest(memberId), HttpStatus.OK);
    }
}
