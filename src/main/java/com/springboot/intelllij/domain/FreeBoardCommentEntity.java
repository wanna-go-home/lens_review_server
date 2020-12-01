package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "free_board_comment")
public class FreeBoardCommentEntity extends CommentBaseEntity {

    @Column(name = "bundle_size")
    private Integer bundleSize = 0;
}
