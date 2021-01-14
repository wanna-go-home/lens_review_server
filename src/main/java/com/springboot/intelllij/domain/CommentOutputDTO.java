package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentOutputDTO extends CommentBaseEntity {

    static enum CommentType {
        ARTICLE, REVIEW;
    }
    private String nickname;
    private CommentType type;

    public CommentOutputDTO(FreeBoardCommentEntity commentEntity, String nickname) {
        super((CommentBaseEntity)commentEntity);
        this.nickname = nickname;
        this.type = CommentType.ARTICLE;
    }

    public CommentOutputDTO(ReviewBoardCommentEntity commentEntity, String nickname) {
        super((CommentBaseEntity)commentEntity);
        this.nickname = nickname;
        this.type = CommentType.REVIEW;
    }
}
