package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "review_board")
public class ReviewBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_id")
    private String account;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    @Column(name = "lens_id")
    private Integer lensId;
}
