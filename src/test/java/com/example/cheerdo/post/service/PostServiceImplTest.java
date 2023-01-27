package com.example.cheerdo.post.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.post.dto.request.LetterRequestDto;
import com.example.cheerdo.post.dto.request.PostRequestDto;
import com.example.cheerdo.post.dto.response.PostResponseDto;
import com.example.cheerdo.post.repository.PostRepository;
import com.example.cheerdo.post.repository.RelationRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class PostServiceImplTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostService postService;


    private static final Logger logger = LoggerFactory.getLogger(PostServiceImplTest.class);

    private Member member;

    private Member friend;

    @BeforeEach
    @Order(0)
    void init() {
        member = Member.builder()
                .id("kim123")
                .bio("안녕 나는 승우야")
                .name("김승우")
                .password("1234")
                .build();

        friend = Member.builder()
                .id("chu123")
                .bio("안녕 나는 츄야")
                .name("츄~")
                .password("1234")
                .build();
        memberRepository.save(member);
        memberRepository.save(friend);
        FriendRelation relation = FriendRelation.builder()
                .id(1L)
                .member(member)
                .friendId("chu123")
                .isFriend(true)
                .hasMessage(false)
                .build();
        FriendRelation relation2 = FriendRelation.builder()
                .id(2L)
                .member(friend)
                .friendId("kim123")
                .isFriend(true)
                .hasMessage(false)
                .build();
        relationRepository.save(relation);
        relationRepository.save(relation2);

    }
    @Test
    @Order(1)
    @DisplayName("편지쓰기")
    @Commit
    void writeLetter() {
        // Given
        LetterRequestDto letterRequestDto =
                new LetterRequestDto(2L, "사랑하는 승우에게", "안녕 나는 츄야 널 사랑해");
        // When
        postService.writeLetter(letterRequestDto);

        // Then

    }

    @Test
    @Order(2)
    @DisplayName("내가 받은 편지 목록 불러오기 ")
    void getMyPosts() throws Exception {
        // Given
        PostRequestDto postRequestDto = new PostRequestDto(false, "kim123");

        // When
        var posts = postService.getMyPosts(postRequestDto);
        logger.info("내 포스트들 -> {}", posts);
        // Then
        assertThat(posts, hasSize(1));
    }

    @Test
    @Order(3)
    @DisplayName("단일 편지 읽기 기능")
    void readPost() throws Exception {
        // Given
        Long letterId = 1L;

        // When
        var output = postService.readLetter(letterId);
        logger.info("읽은 포스트 -> {}", output);

        // Then
        assertThat(output.getMessage(), is("안녕 나는 츄야 널 사랑해"));
        assertThat(output.getSenderName(), is("츄~"));
        assertThat(output.getRelationId(), is(2L));
        assertThat(output.getTitle(), is("사랑하는 승우에게"));

    }

    @Test
    @Order(4)
    @DisplayName("편지를 작성시 보상코인으로 5코인이 들어온다. .")
    void rewardCoinWritePost() {
        // Given
        LetterRequestDto letterRequestDto = new LetterRequestDto(
                1L,
                "새로운 편지 작성",
                "ksw123 -> chu123"
        );
        Member kim123Before = memberRepository.findById("kim123").get();
        int beforeCoinCount = kim123Before.getCoinCount();

        // When
        postService.writeLetter(letterRequestDto);

        //then
        Member kim123After = memberRepository.findById("kim123").get();
        assertThat(kim123After.getCoinCount(), is(beforeCoinCount + 5));

    }

    @Test
    @Order(5)
    @DisplayName("코인이 0개인 상태에서 코인을 읽을 시 에러가 발생한다.")
    void exceptionCoinWritePost() throws Exception {
        // Given

        Member kim123 = memberRepository.findById("kim123").get();
        int beforeCoinCount =  kim123.getCoinCount();
        kim123.useCoin(beforeCoinCount);
        memberRepository.save(kim123);

        PostRequestDto postRequestDto = new PostRequestDto(false, "kim123");

        PostResponseDto postResponseDto = (PostResponseDto) postService.getMyPosts(postRequestDto).get(0);
        Long letterId = postResponseDto.getLetterId();

        // When
        try {
            postService.readLetter(letterId);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("보유하고 있는 코인이 부족합니다."));
        }

    }
}