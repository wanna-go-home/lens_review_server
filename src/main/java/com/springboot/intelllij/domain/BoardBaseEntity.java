package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BoardBaseEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "view_cnt")
    private Integer viewCnt = 0;

    @Column(name = "reply_cnt")
    private Integer replyCnt = 0;

    public BoardBaseEntity(Integer accountId, String title, String content) {
        this.accountId = accountId;
        this.title = title;
        this.content = content;
    }

    public void increaseViewCnt() { this.viewCnt++; }

    public void increaseReplyCnt() {
        this.replyCnt++;
    }

    public void decreaseReplyCnt() { this.replyCnt--; }
}
