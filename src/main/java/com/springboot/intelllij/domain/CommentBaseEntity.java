package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.stream.events.Comment;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class CommentBaseEntity extends BaseEntity {
    @Column(name = "account_id")
    private String email;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "content")
    private String content;

    @Column(name = "like_cnt")
    private Integer likeCnt = 0;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("UTC"));

    @Column(name = "depth")
    private Integer depth = 0;

    @Column(name = "bundle_id")
    private Integer bundleId;

    @Column(name = "bundle_size")
    private Integer bundleSize = 0;

    public void increaseBundleSize() {
        this.bundleSize++;
    }

    public CommentBaseEntity(CommentBaseEntity commentBase) {
        this.email = commentBase.getEmail();
        this.postId = commentBase.getPostId();
        this.content = commentBase.getContent();
        this.likeCnt = commentBase.getLikeCnt();
        this.createdAt = commentBase.getCreatedAt();
        this.depth = commentBase.getDepth();
        this.bundleId = commentBase.getBundleId();
        this.bundleSize = commentBase.getBundleSize();
    }
}
