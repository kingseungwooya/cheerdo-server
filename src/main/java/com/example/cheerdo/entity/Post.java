package com.example.cheerdo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

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

    private String title;
    @Lob
    private String message;

    @Column(name = "open_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isOpen;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "send_date")
    private LocalDateTime sendDateTime;

    @Builder
    public Post(Long id, FriendRelation relation, String title, String message, boolean isOpen, LocalDateTime sendDateTime) {
        this.id = id;
        this.relation = relation;
        this.title = title;
        this.message = message;
        this.isOpen = isOpen;
        this.sendDateTime = sendDateTime;
    }
}
