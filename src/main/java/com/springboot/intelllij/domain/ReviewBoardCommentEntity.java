package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table (name = "review_board_comment")
@DynamicUpdate
@DynamicInsert
public class ReviewBoardCommentEntity extends CommentBaseEntity {
}

