package com.example.cheerdo.entity;

import com.example.cheerdo.post.dto.response.LetterResponseDto;
import com.example.cheerdo.post.dto.response.PostResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "relation_id")
    private FriendRelation relation;

    private String receiverId;

    private String senderName;

    private String title;
    @Lob
    private String message;

    @Column(name = "open_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isOpen;

    // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    @Column(name = "send_date")
    private LocalDate sendDateTime;

    @Builder
    public Post(Long id, FriendRelation relation,String receiverId, String senderName, String title, String message, boolean isOpen, LocalDate sendDateTime) {
        this.id = id;
        this.relation = relation;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.title = title;
        this.message = message;
        this.isOpen = isOpen;
        this.sendDateTime = sendDateTime;
    }

    public PostResponseDto entityToPostResponseDto() {
        return PostResponseDto.builder()
                .letterId(id)
                .senderId(relation.getFriendId())
                .senderName(senderName)
                .sendDate(sendDateTime)
                .build();
    }

    public LetterResponseDto entityToLetterResponseDto() {
        return LetterResponseDto.builder()
                .letterId(id)
                .senderName(senderName)
                .message(message)
                .title(title)
                .relationId(relation.getId())
                .sendDate(sendDateTime)
                .build();
    }
}
