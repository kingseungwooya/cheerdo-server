package com.example.cheerdo.entity;

import com.example.cheerdo.member.dto.response.MemberInfoResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "d_plus_day")
    private int dPlusDay;

    @ManyToMany(fetch = FetchType.EAGER)
    //@Builder.Default
    private Collection<Role> roles = new ArrayList<>();

    @Lob
    @Column(name = "member_image")
    private String memberImage;

    @Builder
    public Member(String id, String password, String name, String bio, int coinCount, double habitProgress, List<Role> roles) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.coinCount = coinCount;
        this.habitProgress = habitProgress;
        this.dPlusDay = 0;
        this.roles = roles;
    }

    public void rewardCoin(int reward) {
        this.coinCount = coinCount + reward;
    }

    public void useCoin(int cost) {
        this.coinCount = coinCount - cost;
    }

    public MemberInfoResponseDto to(int dPlusCount) {
        return MemberInfoResponseDto.builder()
                .memberId(id)
                .bio(bio)
                .name(name)
                .coinCount(coinCount)
                .image(memberImage)
                .dPlusCount(dPlusCount)
                .build();
    }

    public void updateMemberImage(String uploadImage) {
        this.memberImage = uploadImage;
    }

    public void updateBio(String updatedBio) {
        this.bio = updatedBio;
    }

    public void updateName(String updatedName) {
        this.name = updatedName;
    }



}
