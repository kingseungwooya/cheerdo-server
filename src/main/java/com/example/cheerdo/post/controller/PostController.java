package com.example.cheerdo.post.controller;

import com.example.cheerdo.post.dto.response.PostResponseDto;
import com.example.cheerdo.post.dto.response.PostStatusResponse;
import com.example.cheerdo.post.dto.response.letterResponseDto;
import com.github.javafaker.Faker;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/post")

public class PostController {
    private final Faker faker = new Faker(Locale.KOREAN);
    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/new-post")
    @ApiOperation(value = "PostBox를 눌렀을때 나오는 읽지 않은 편지들을 반환"
            , notes = "사용자의 ID를 통해 조회한다. ")
    @ApiResponses( {
            @ApiResponse( code = 200, message = "status ok")
    })
    public ResponseEntity<?> getMyNewPost(@RequestParam Long userId) {
        logger.info("request userId is -> {}", userId);
        List<PostResponseDto> responseDtos = List.of(
                new PostResponseDto("senderID1", faker.leagueOfLegends().champion(), faker.number().randomNumber()),
                new PostResponseDto("senderID2", faker.leagueOfLegends().champion(), faker.number().randomNumber()),
                new PostResponseDto("senderID3", faker.leagueOfLegends().champion(), faker.number().randomNumber())
        );
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/letter")
    public ResponseEntity<?> readLetter(@RequestParam Long letterId) {
        // 여기서 처리
        logger.info("request letterId is -> {}", letterId);
        if (letterId == 0) {
            // 406
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        letterResponseDto responseDto =
                new letterResponseDto(faker.number().randomNumber(), "senderId1", faker.leagueOfLegends().champion(),
                        faker.book().title(), faker.lorem().sentence(30));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam Long userId) {
        logger.info("request userId is -> {}", userId);
        return new ResponseEntity<>(
                new PostStatusResponse(10, 100), HttpStatus.OK);
    }
}