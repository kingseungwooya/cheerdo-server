package com.example.cheerdo.login.service;

import com.example.cheerdo.entity.Role;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.dto.response.MemberInfoResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface MemberService {

    void join(JoinRequestDto joinRequestDto);

    Role saveRole(Role role);

    void addToRoleToUser(String userid, String roleName);

    MemberInfoResponseDto getInfoById(String memberId);
    
    boolean checkUsernameDuplication(String memberId);

    String uploadImage(MultipartFile uploadImage, String memberId) throws IOException;
}
