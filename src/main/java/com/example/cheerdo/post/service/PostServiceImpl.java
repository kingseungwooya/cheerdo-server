package com.example.cheerdo.post.service;

import com.example.cheerdo.common.sort.SortUtil;
import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Post;
import com.example.cheerdo.entity.Todo;
import com.example.cheerdo.post.dto.request.LetterRequestDto;
import com.example.cheerdo.post.dto.request.PostRequestDto;
import com.example.cheerdo.post.dto.request.TotalPostRequestDto;
import com.example.cheerdo.post.dto.response.LetterResponseDto;
import com.example.cheerdo.repository.MemberRepository;
import com.example.cheerdo.repository.PostRepository;
import com.example.cheerdo.repository.RelationRepository;
import com.example.cheerdo.repository.TodoRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final static int READ_LETTER_COIN_COST = 2;
    private final static int WRITE_LETTER_COIN_REWARD = 5;

    private final PostRepository postRepository;
    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;


    @Override
    public List<?> getMyPosts(PostRequestDto postRequestDto) throws IllegalArgumentException {
        // 개봉된 편지들
        if (postRequestDto.isOpen()) {
            List<Post> letters = postRepository.findAllByReceiverIdAndIsOpenAndSendDateTimeBetween(
                    postRequestDto.getMemberId(),
                    postRequestDto.isOpen(),
                    postRequestDto.getStartDate(),
                    postRequestDto.getEndDate(),
                    SortUtil.sort(postRequestDto.getSortType(), "sendDateTime")
            ).orElseThrow(() -> new IllegalArgumentException("you have no friend"));
            return letters.stream()
                    .map(post -> post.entityToLetterResponseDto())
                    .collect(Collectors.toList());
        }
        // 미개봉된 편지들
        List<Post> posts = postRepository.findAllByReceiverIdAndIsOpen(
                postRequestDto.getMemberId(),
                postRequestDto.isOpen(),
                SortUtil.sort(postRequestDto.getSortType(), "sendDateTime")
        ).orElseThrow(() -> new IllegalArgumentException("you have no friend"));
        return posts.stream()
                .map(post -> post.entityToPostResponseDto())
                .collect(Collectors.toList());
    }

    @Override
    public LetterResponseDto readLetter(Long letterId) throws Exception {
        // Coin에 대한 처리 해야함 읽을 시 편지 상태로 읽음으로 변경해줘야함
        Post post = postRepository.findById(letterId).get();
        post.openLetter();
        postRepository.save(post);
        // 읽기 요청시 coin에 대한 서비스
        Member member = memberRepository.findById(post.getReceiverId()).get();
        int beforeCoinCount = member.getCoinCount();
        if (beforeCoinCount < READ_LETTER_COIN_COST) {
            throw new Exception("you need more coin");
        }
        member.useCoin(READ_LETTER_COIN_COST);
        memberRepository.save(member);
        return post.entityToLetterResponseDto();
    }

    @Override
    @Transactional
    public void writeLetter(LetterRequestDto letterRequestDto) {
        FriendRelation relation = relationRepository.findById(letterRequestDto.getRelationId()).get();
        Member member = relation.getMember();
        member.rewardCoin(WRITE_LETTER_COIN_REWARD);
        memberRepository.save(member);

        Todo todo = todoRepository.findById(letterRequestDto.getTodoId()).get();
        postRepository.save(letterRequestDto.dtoToPostEntity(relation, todo));
    }

    @Override
    public List<?> getAllPosts(TotalPostRequestDto totalPostRequestDto) {
        Optional<List<Post>> posts = postRepository.findAllByReceiverIdAndSendDateTimeBetween(
                totalPostRequestDto.getMemberId(),
                totalPostRequestDto.getStartDate(),
                totalPostRequestDto.getEndDate(),
                SortUtil.sort(totalPostRequestDto.getSortType(), "sendDateTime")
        );
        List responsePosts = new ArrayList();
        if (posts.isEmpty()) {
            return responsePosts;
        }
        responsePosts.addAll(
                posts.get().stream()
                        .filter( p -> p.isOpen())
                        .map( s -> s.entityToLetterResponseDto())
                        .collect(Collectors.toList())
        );
        responsePosts.addAll(
                posts.get().stream()
                        .filter( p -> !p.isOpen())
                        .map( s-> s.entityToPostResponseDto())
                        .collect(Collectors.toList())
        );
        return responsePosts;
    }

}
