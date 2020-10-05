package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "like_cnt")
    private Integer likeCnt = 0;

    @Column(name = "reply_cnt")
    private Integer replyCnt = 0;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "lens_id")
    private Integer lensId;
}
