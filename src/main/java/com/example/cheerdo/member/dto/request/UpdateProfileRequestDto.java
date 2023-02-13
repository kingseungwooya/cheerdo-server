package com.example.cheerdo.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UpdateProfileRequestDto {
    private String memberId;
    private String updateName;
    private String updateBio;
    private MultipartFile uploadImage;
}
