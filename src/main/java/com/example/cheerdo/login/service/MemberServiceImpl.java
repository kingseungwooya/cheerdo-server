package com.example.cheerdo.login.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Role;
import com.example.cheerdo.login.config.CustomUser;
import com.example.cheerdo.login.dto.request.JoinRequestDto;
import com.example.cheerdo.login.dto.response.MemberInfoResponseDto;
import com.example.cheerdo.login.repository.RoleRepository;
import com.example.cheerdo.post.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Override
    public void join(JoinRequestDto joinRequestDto) {
        String encodedPassword = passwordEncoder.encode(joinRequestDto.getPassword());
        joinRequestDto.setPassword(encodedPassword);
        memberRepository.save(joinRequestDto.dtoToMember());
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addToRoleToUser(String userId, String roleName) {
        logger.info("회원:{} 에게 권한:{} 부여 완료.", userId, roleName);
        Member member = memberRepository.findById(userId).get();
        Role role = roleRepository.findByName(roleName);
        logger.info("rolename -> {} 하고 role -> {} 적용되었는지 확인", roleName, role);
        member.getRoles().add(role);
        logger.info("role 추가되었는지 확인{}", member);
    }

    @Override
    public MemberInfoResponseDto getInfoById(String memberId) {
        return memberRepository.findById(memberId).get().to();
    }

    @Override
    public boolean checkUsernameDuplication(String memberId) {
        return memberRepository.existsById(memberId);
    }

    // 로그인 요청시 일어나는 service
    @Override
    public CustomUser loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 id의 회원은 DB에 존재하지 않음"));
        logger.info("멤버가 존재함: {} ", member.getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        // 편지 갯수를 반환하기 위해선 post repo를 통해
        // recevier_id가 본인이고 isOpen이 false인 값을 찾아 개수를 센다
        int letterCount = 0; // 추후 구현
        // spring secirity 사용자를 return
        return new CustomUser(member.getId(), member.getPassword(), authorities, member.getCoinCount(), letterCount);
        /* CustomUser.builder()
                .clone(User.builder()
                        .username(member.getId())
                        .password(member.getPassword())
                        .authorities(authorities))
                .coinCount(member.getCoinCount())
                .newLetterCount(letterCount)
                .build();*/
    }
}
