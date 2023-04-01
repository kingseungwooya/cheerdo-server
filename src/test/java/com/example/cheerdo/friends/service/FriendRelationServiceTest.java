package com.example.cheerdo.friends.service;

import com.example.cheerdo.entity.Member;
import com.example.cheerdo.friends.dto.request.SendRequestDto;
import com.example.cheerdo.friends.dto.response.FollowerResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.PostRepository;
import com.example.cheerdo.repository.PostRequestRepository;
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
class FriendRelationServiceTest {

    private final Logger logger = LoggerFactory.getLogger(FriendRelationServiceTest.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostRequestRepository postRequestRepository;

    @Autowired
    private PostRequestService postRequestService;

    @Autowired
    private FriendRelationService friendRelationService;

    private Member kim;

    private Member park;

    private long relationId;

    // given
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

        SendRequestDto sendRequestDto =
                new SendRequestDto(
                        kim.getId(),
                        park.getName()
                );

        followService.sendFollow(sendRequestDto);
    }

    @Test
    @DisplayName("친구 목록을 불러올 수 있다.")
    void getFriends() {
        // given
        relationId = followService.getSentFollow(kim.getId()).get(0).getRelationId();
        followService.accept(relationId);

        // when
        List<FollowerResponseDto> friends = friendRelationService.getFriends(kim.getId());

        // then
        assertThat(friends.size(), is(1));
    }

    @Test
    @DisplayName("친구를 삭제하고 삭제 시 관련된 post와 post_request는 삭제된다.")
    void deleteFriend() {
        // given
        relationId = followService.getSentFollow(kim.getId()).get(0).getRelationId();
        followService.accept(relationId);

        // when
        postRequestService.sendPostRequest(relationId);
        friendRelationService.deleteFriend(relationId);
        // then
        List<FollowerResponseDto> friends = friendRelationService.getFriends(kim.getId());
        assertThat(friends.size(), is(0));

        assertThat(postRepository.countAllByReceiverId(park.getId()), is(0L));
        assertThat(postRequestRepository.findAllByFriendRelation_FriendId(kim.getId()).get(), empty());

    }
}