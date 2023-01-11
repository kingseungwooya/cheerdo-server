package com.example.cheerdo.post.controller;

import com.example.cheerdo.post.dto.response.PostResponseDto;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {
    private final Faker faker = new Faker();
    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping("")
    public ResponseEntity<?> getMyPost(@RequestParam Long userId) {
        List<PostResponseDto> responseDtos = List.of(
                new PostResponseDto("senderID1", faker.leagueOfLegends().champion(), "receiverID1"),
                new PostResponseDto("senderID2", faker.leagueOfLegends().champion(), "receiverID2"),
                new PostResponseDto("senderID3", faker.leagueOfLegends().champion(), "receiverID3")
                );
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<?> getLetter(@RequestParam Long letterId) {
        // 여기서 처리
        List<PostResponseDto> responseDtos = List.of(
                new PostResponseDto("senderID1", faker.leagueOfLegends().champion(), "receiverID1"),
                new PostResponseDto("senderID2", faker.leagueOfLegends().champion(), "receiverID2"),
                new PostResponseDto("senderID3", faker.leagueOfLegends().champion(), "receiverID3")
        );
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}