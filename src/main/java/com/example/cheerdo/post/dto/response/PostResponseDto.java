package com.example.cheerdo.post.dto.response;

import lombok.Data;

@Data
public class PostResponseDto {
    private final String senderId;

    private final String senderName;
    private final String receiverId;

}
