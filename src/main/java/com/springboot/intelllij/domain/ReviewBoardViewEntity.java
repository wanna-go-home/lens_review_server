package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "review_board_view")
public class ReviewBoardViewEntity extends BoardBaseEntity {
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "lens_id")
    private Integer lensId;

    public ReviewBoardViewEntity(ReviewBoardViewEntity reviewBoardEntity) {
        this.setId(reviewBoardEntity.getId());
        this.setAccountId(reviewBoardEntity.getAccountId());
        this.setTitle(reviewBoardEntity.getTitle());
        this.setContent(reviewBoardEntity.getContent());
        this.setViewCnt(reviewBoardEntity.getViewCnt());
        this.setLikeCnt(reviewBoardEntity.getLikeCnt());
        this.setReplyCnt(reviewBoardEntity.getReplyCnt());
        this.setCreatedAt(reviewBoardEntity.getCreatedAt());
        this.setNickname(reviewBoardEntity.getNickname());
        this.setLensId(reviewBoardEntity.getLensId());
    }

    public ReviewBoardViewEntity(ReviewBoardEntity reviewBoardEntity, String nickname) {
        this.setId(reviewBoardEntity.getId());
        this.setAccountId(reviewBoardEntity.getAccountId());
        this.setContent(reviewBoardEntity.getContent());
        this.setLikeCnt(reviewBoardEntity.getLikeCnt());
        this.setCreatedAt(reviewBoardEntity.getCreatedAt());
        this.setTitle(reviewBoardEntity.getTitle());
        this.setViewCnt(reviewBoardEntity.getViewCnt());
        this.setReplyCnt(reviewBoardEntity.getReplyCnt());
        this.nickname = nickname;
        this.setLensId(reviewBoardEntity.getLensId());
    }
}
