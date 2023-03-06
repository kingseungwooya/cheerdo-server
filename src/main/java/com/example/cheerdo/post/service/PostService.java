package com.example.cheerdo.post.service;

import com.example.cheerdo.post.dto.request.LetterRequestDto;
import com.example.cheerdo.post.dto.request.PostRequestDto;
import com.example.cheerdo.post.dto.response.LetterResponseDto;

import java.util.List;

public interface PostService {
    List<?> getMyPosts(PostRequestDto postRequestDto) throws IllegalArgumentException;

    LetterResponseDto readLetter(Long letterId) throws Exception;

    void writeLetter(LetterRequestDto letterRequestDto);
}
