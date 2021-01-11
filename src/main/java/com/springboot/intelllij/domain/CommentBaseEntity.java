package com.springboot.intelllij.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public abstract class CommentBaseEntity extends BaseEntity {
    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "bundle_id")
    private Integer bundleId;

    @Column(name = "bundle_size")
    private Integer bundleSize;

    public void increaseBundleSize() {
        this.bundleSize++;
    }

    public void decreaseBundleSize() {
        this.bundleSize--;
    }

    public CommentBaseEntity(CommentBaseEntity commentBase) {
        this.id = commentBase.getId();
        this.accountId = commentBase.getAccountId();
        this.postId = commentBase.getPostId();
        this.content = commentBase.getContent();
        this.likeCnt = commentBase.getLikeCnt();
        this.createdAt = commentBase.getCreatedAt();
        this.depth = commentBase.getDepth();
        this.bundleId = commentBase.getBundleId();
        this.bundleSize = commentBase.getBundleSize();
        this.isAuthor = commentBase.getIsAuthor();
}
