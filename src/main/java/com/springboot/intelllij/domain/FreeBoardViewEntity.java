package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "free_board_view")
public class FreeBoardViewEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "account_id")
    private String account;

    @Column(name = "name")
    private String nickname;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @Column(name = "reply_cnt")
    private Integer replyCnt;

    @Column(name = "created_at")
    private Date created_at;
}
