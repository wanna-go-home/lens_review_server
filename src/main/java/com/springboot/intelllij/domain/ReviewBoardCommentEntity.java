package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table (name = "review_board_comment")
public class ReviewBoardCommentEntity extends CommentBaseEntity {
}

