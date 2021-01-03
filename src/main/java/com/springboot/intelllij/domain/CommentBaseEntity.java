package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class CommentBaseEntity extends BaseEntity {
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "content")
    private String content;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private ZonedDateTime createdAt;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "bundle_id")
    private Integer bundleId;

    @Column(name = "bundle_size")
    private Integer bundleSize;

    public void increaseBundleSize() {
        this.bundleSize++;
    }

    public CommentBaseEntity(CommentBaseEntity commentBase) {
        this.setId(commentBase.getId());
        this.accountId = commentBase.getAccountId();
        this.postId = commentBase.getPostId();
        this.content = commentBase.getContent();
        this.likeCnt = commentBase.getLikeCnt();
        this.createdAt = commentBase.getCreatedAt();
        this.depth = commentBase.getDepth();
        this.bundleId = commentBase.getBundleId();
        this.bundleSize = commentBase.getBundleSize();
    }
}
