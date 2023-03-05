package com.example.cheerdo.login.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.login.security.CustomUser;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final PostRepository postRepository;
    @Override
    public CustomUser loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 id의 회원은 DB에 존재하지 않음"));
        logger.info("멤버가 존재함: {} ", member.getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        });
        int letterCount = postRepository.countAllByReceiverIdAndIsOpen(memberId, false).orElse(0);

        return new CustomUser(member.getId(), member.getPassword(), authorities
                , member.getCoinCount(), letterCount, member.getName());
    }
}
