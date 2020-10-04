package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "review_board_view")
public class ReviewBoardViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @Column(name = "reply_cnt")
    private Integer replyCnt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "lens_id")
    private Integer lensId;
}
