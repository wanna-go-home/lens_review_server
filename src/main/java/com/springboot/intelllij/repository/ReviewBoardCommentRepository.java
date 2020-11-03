package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardCommentRepository extends JpaRepository<ReviewBoardCommentEntity, Integer> {
}
