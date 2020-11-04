package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
public class CommentBaseEntity extends BaseEntity {
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "content")
    private String content;

    @Column(name = "like_cnt")
    private Integer likeCnt = 0;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    @Column(name = "depth")
    private Integer depth = 0;

    @Column(name = "bundle_id")
    private Integer bundleId;
}
