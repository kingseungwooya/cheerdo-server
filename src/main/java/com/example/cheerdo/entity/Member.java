package com.example.cheerdo.entity;

import com.example.cheerdo.login.dto.response.MemberInfoResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;

    @Column(name = "member_pw")
    private String password;

    private String name;

    @Lob
    private String bio;

    @Column(name = "coin_count")
    private int coinCount;

    @Column(name = "habit_progress")
    private double habitProgress;

    @ManyToMany(fetch = FetchType.EAGER)
    //@Builder.Default
    private Collection<Role> roles = new ArrayList<>();

    @Lob
    @Column(name = "member_image")
    private byte[] memberImage;

    @Builder
    public Member(String id, String password, String name, String bio, int coinCount, double habitProgress, List<Role> roles) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.coinCount = coinCount;
        this.habitProgress = habitProgress;
        this.roles = roles;
    }

    public void rewardCoin(int reward) {
        this.coinCount = coinCount + reward;
    }

    public void useCoin(int cost) {
        this.coinCount = coinCount - cost;
    }

    public MemberInfoResponseDto to() {
        return MemberInfoResponseDto.builder()
                .memberId(id)
                .habitProgress(habitProgress)
                .bio(bio)
                .name(name)
                .coinCount(coinCount)
                .build();
    }

    public void updateMemberImage(byte[] uploadImage) {
        this.memberImage = uploadImage;
    }

}
