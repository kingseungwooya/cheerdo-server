package com.example.cheerdo.post.service;

import com.example.cheerdo.entity.FriendRelation;
import com.example.cheerdo.entity.Member;
import com.example.cheerdo.entity.Post;
import com.example.cheerdo.post.dto.request.LetterRequestDto;
import com.example.cheerdo.post.dto.request.PostRequestDto;
import com.example.cheerdo.post.dto.response.LetterResponseDto;
import com.example.cheerdo.post.dto.response.PostResponseDto;
import com.example.cheerdo.post.repository.PostRepository;
import com.example.cheerdo.post.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.management.relation.Relation;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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


    @Override
    public List<?> getMyPosts(PostRequestDto postRequestDto) throws Exception {
        List<Post> posts = postRepository.findAllByReceiverIdAndIsOpenOrderBySendDateTime
                        (postRequestDto.getMemberId(), postRequestDto.isOpen())
                .orElseThrow(() -> new Exception("you have no friend"));
        if (postRequestDto.isOpen()) {
            return posts.stream()
                    .map(post -> post.entityToLetterResponseDto())
                    .collect(Collectors.toList());
        }
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
        postRepository.save(letterRequestDto.dtoToPostEntity(relation));
    }

    private List<FriendRelation> getMyRelation(Long memberId) throws Exception {
        Optional<List<FriendRelation>> friendRelation = relationRepository.findAllByMember_Id(memberId);
        return friendRelation.orElseThrow(() -> new Exception("현재 등록되어 있는 친구가 없습니다."));
    }
}
