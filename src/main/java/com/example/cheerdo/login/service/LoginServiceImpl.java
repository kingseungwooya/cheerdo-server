package com.example.cheerdo.login.service;

import com.example.cheerdo.entity.Role;
import com.example.cheerdo.entity.enums.RoleName;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.repository.RoleRepository;
import com.example.cheerdo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {
    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(JoinRequestDto joinRequestDto) {
        String encodedPassword = passwordEncoder.encode(joinRequestDto.getPassword());
        joinRequestDto.setPassword(encodedPassword);
        Role role = roleRepository.findByName(RoleName.ROLE_USER);
        memberRepository.save(joinRequestDto.dtoToMember(role));
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addToRoleToUser(String userId, RoleName roleName) {
        /*
        logger.info("회원:{} 에게 권한:{} 부여 완료.", userId, roleName);
        Member member = memberRepository.findById(userId).get();
        Role role = roleRepository.findByName(roleName);
        logger.info("rolename -> {} 하고 role -> {} 적용되었는지 확인", roleName, role.getName());
        member.getRoles().add(role);
        logger.info("role 추가되었는지 확인{}", member);
        */
    }



    @Override
    public boolean checkUsernameDuplication(String memberId) {
        return memberRepository.existsById(memberId);
    }

}
