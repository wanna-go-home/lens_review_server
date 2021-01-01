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
    @Column(name = "name")
    private String nickname;

    @Column(name = "lens_id")
    private Integer lensId;

    public ReviewBoardViewEntity(ReviewBoardViewEntity reviewBoardEntity) {
        this.setEmail(reviewBoardEntity.getEmail());
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
