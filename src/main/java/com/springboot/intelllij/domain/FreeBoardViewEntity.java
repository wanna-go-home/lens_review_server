package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private ZonedDateTime createdAt;
}
