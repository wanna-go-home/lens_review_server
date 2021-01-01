package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table (name = "review_board_comment")
@NoArgsConstructor
public class ReviewBoardCommentEntity extends CommentBaseEntity {
}

