package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "free_board")
public class FreeBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "view_cnt")
    private Integer viewCnt;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_cnt")
    private Integer likeCnt;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_cnt")
    private Integer replyCnt;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created_at")
    private Date created_at;
}
