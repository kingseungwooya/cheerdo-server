package com.example.cheerdo.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateProfileRequestDto {
    private String memberId;
    private String updateName;
    private String updateBio;
    private String uploadImage;

    @Builder
    public UpdateProfileRequestDto(String memberId, String updateName, String updateBio, String uploadImage) {
        this.memberId = memberId;
        this.updateName = updateName;
        this.updateBio = updateBio;
        this.uploadImage = uploadImage;
    }
}
