package com.example.cheerdo.login.service;

import com.example.cheerdo.entity.Role;
import com.example.cheerdo.entity.enums.RoleName;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    void join(JoinRequestDto joinRequestDto);

    Role saveRole(Role role);

    void addToRoleToUser(String userid, RoleName roleName);

    
    boolean checkUsernameDuplication(String memberId);

}
