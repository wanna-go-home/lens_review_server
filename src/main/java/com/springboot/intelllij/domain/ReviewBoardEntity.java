package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "review_board")
public class ReviewBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "lens_id")
    private Integer lensId;
}
