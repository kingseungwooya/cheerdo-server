package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.RelationRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@Transactional
@Rollback(true)
@RunWith(SpringRunner.class)
class FollowServiceTest {

    private final Logger logger = LoggerFactory.getLogger(FollowServiceTest.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private FriendRelationService friendRelationService;

    private Member kim;

    private Member park;

    @BeforeEach
    void setUp() {
        kim = Member.builder()
                .name("kim")
                .id("kim")
                .build();
        park = Member.builder()
                .name("park")
                .id("park")
                .build();
        memberRepository.saveAll(List.of(kim, park));

    }

    @Test
    @DisplayName("친구요청을 보내고 보낸 친구요청, 받은 친구요청을 확인할 수 있다.")
    void FollowTest() {
        // given
        SendRequestDto sendRequestDto =
                new SendRequestDto(
                        kim.getId(),
                        park.getName()
                );

        // when
        followService.sendFollow(sendRequestDto);

        Long relationId = followService.getSentFollow(kim.getId()).get(0).getRelationId();
        logger.info("relation id is -> {}", relationId);

        // then
        assertThat(relationId,
                is(followService.getFollowRequest(park.getId()).get(0)
                        .getRelationId()));
    }

    @Test
    @DisplayName("친구 요청을 거절하는 기능")
    void refuse() {
        // given
        SendRequestDto sendRequestDto =
                new SendRequestDto(
                        kim.getId(),
                        park.getName()
                );
        followService.sendFollow(sendRequestDto);

        // when
        FollowerResponseDto followRequest = followService.getFollowRequest(park.getId()).get(0);
        long requestId = followRequest.getRelationId();
        followService.refuse(requestId);

        // then
        assertThat(followService.getSentFollow(kim.getId()), empty());
        assertThat(followService.getFollowRequest(park.getId()), empty());
        assertThat(friendRelationService.getFriends(kim.getId()), empty());
    }

    @Test
    @DisplayName("친구 요청을 수락하는 기능")
    void accept() {
        // given
        SendRequestDto sendRequestDto =
                new SendRequestDto(
                        kim.getId(),
                        park.getName()
                );
        followService.sendFollow(sendRequestDto);

        // when
        FollowerResponseDto followRequest = followService.getFollowRequest(park.getId()).get(0);
        long requestId = followRequest.getRelationId();
        followService.accept(requestId);

        // then
        assertThat(followService.getSentFollow(kim.getId()), empty());
        assertThat(followService.getFollowRequest(park.getId()), empty());
        assertThat(friendRelationService.getFriends(kim.getId()).size(), is(1));
        assertThat(friendRelationService.getFriends(park.getId()).get(0).getMemberId()
                , is(kim.getId()));
    }
}