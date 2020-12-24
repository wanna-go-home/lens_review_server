package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBoardCommentRepository extends JpaRepository<ReviewBoardCommentEntity, Integer> {
    List<ReviewBoardCommentEntity> findByPostId(Integer integer);

    List<ReviewBoardCommentEntity> findByEmail(String email);

    void deleteByPostId(Integer postId);
}
