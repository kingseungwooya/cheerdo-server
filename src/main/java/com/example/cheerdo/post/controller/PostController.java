package com.example.cheerdo.post.controller;

import com.example.cheerdo.post.dto.request.LetterRequestDto;
import com.example.cheerdo.post.dto.request.PostRequestDto;
import com.example.cheerdo.post.dto.response.PostResponseDto;
import com.example.cheerdo.post.dto.response.PostStatusResponse;
import com.example.cheerdo.post.dto.response.LetterResponseDto;
import com.example.cheerdo.post.service.PostService;
import com.github.javafaker.Faker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/member/post")
@RequiredArgsConstructor
public class PostController {
    private final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @GetMapping("/posts")
    @ApiOperation(value = "PostBox를 눌렀을때 나오는 읽지 않은 편지들을 반환"
            , notes = "사용자의 ID와 열려있는지 닫혀있는지 boolean 값을 입력받는다. memberid는 dummydata로 kim123을 이용한다. ")
    @ApiResponses( {
            @ApiResponse( code = 200, message = "status ok")
    })
    public ResponseEntity<?> getMyNewPost(@ModelAttribute PostRequestDto postRequestDto) {
        logger.info("request is -> {}", postRequestDto.toString());
        try {
            List<PostResponseDto> postResponseDtos = (List<PostResponseDto>) postService.getMyPosts(postRequestDto);
            return new ResponseEntity<>(postResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/letter")
    @ApiOperation(value = "미개봉된 편지를 읽기 요청시 실행된다. "
            , notes = "편지의 고유번호를 통해 읽기 요청시 실행된다.")
    public ResponseEntity<?> readLetter(@RequestParam Long letterId) {
        try {
            return new ResponseEntity<>(postService.readLetter(letterId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "편지를 쓸 수 있다.")
    @PostMapping("/letter")
    public ResponseEntity<String> writeLetter(@RequestBody LetterRequestDto letterRequestDto) {
        postService.writeLetter(letterRequestDto);
        String message = "Send letter success";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam Long userId) {
        logger.info("request userId is -> {}", userId);
        return new ResponseEntity<>(
                new PostStatusResponse(10, 100), HttpStatus.OK);
    }
}