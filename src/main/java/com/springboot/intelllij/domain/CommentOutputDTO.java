package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommentOutputDTO extends CommentBaseEntity {
    private String nickname;

    public CommentOutputDTO(FreeBoardCommentEntity commentEntity, String nickname) {
        super((CommentBaseEntity)commentEntity);
        this.nickname = nickname;
    }

    public CommentOutputDTO(ReviewBoardCommentEntity commentEntity, String nickname) {
        super((CommentBaseEntity)commentEntity);
        this.nickname = nickname;
    }
}
