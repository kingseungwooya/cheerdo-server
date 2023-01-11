package com.example.cheerdo.post.dto.response;

import lombok.Data;

@Data
public class OpenedPostResponseDto {
    private String senderId;
    private String receiverId;

    private String title;
    private String message;
    // 열람되었는지 확인하는 변수 이건 entity에 추가하는게 맞을 듯 싶다.
    // private boolean isOpened;
}
