package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "free_board_view")
@DynamicInsert
@NoArgsConstructor
public class FreeBoardViewEntity extends BoardBaseEntity {
    @Column(name = "nickname")
    private String nickname;

    public FreeBoardViewEntity(Integer accountId, String title, String content, String nickname) {
        super(accountId, title, content);
        this.nickname = nickname;
    }

    public FreeBoardViewEntity(FreeBoardEntity freeBoardEntity, String nickname) {
        this.setId(freeBoardEntity.getId());
        this.setAccountId(freeBoardEntity.getAccountId());
        this.setContent(freeBoardEntity.getContent());
        this.setLikeCnt(freeBoardEntity.getLikeCnt());
        this.setCreatedAt(freeBoardEntity.getCreatedAt());
        this.setTitle(freeBoardEntity.getTitle());
        this.setViewCnt(freeBoardEntity.getViewCnt());
        this.setReplyCnt(freeBoardEntity.getReplyCnt());
        this.nickname = nickname;
    }
}
