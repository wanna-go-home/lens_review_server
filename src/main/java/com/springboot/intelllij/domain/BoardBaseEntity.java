package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BoardBaseEntity extends BaseEntity {
    @Column(name = "account_id")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view_cnt")
    private Integer viewCnt = 0;

    @Column(name = "like_cnt")
    private Integer likeCnt = 0;

    @Column(name = "reply_cnt")
    private Integer replyCnt = 0;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("UTC"));

    public BoardBaseEntity(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
