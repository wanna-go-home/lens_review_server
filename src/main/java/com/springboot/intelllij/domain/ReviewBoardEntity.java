package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review_board")
@DynamicUpdate
@DynamicInsert
public class ReviewBoardEntity extends BoardBaseEntity {
    @Formula("(SELECT a.nickname FROM account a WHERE a.id = account_id)")
    private String nickname;

    @Column(name = "lens_id")
    private Integer lensId;

    public ReviewBoardEntity(ReviewBoardDto reviewBoardDto, Integer accountId) {
        super(accountId, reviewBoardDto.getTitle(), reviewBoardDto.getContent());
        this.lensId = reviewBoardDto.getLensId();
    }

    public ReviewBoardEntity(ReviewBoardEntity reviewBoardEntity) {
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
}
