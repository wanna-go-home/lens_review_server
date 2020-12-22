package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBoardCommentRepository extends JpaRepository<ReviewBoardCommentEntity, Integer> {
    List<ReviewBoardCommentEntity> findByPostId(Integer integer);

    List<ReviewBoardCommentEntity> findByAccountId(String accountId);

    List<ReviewBoardCommentEntity> findByPostIdAndDepth(Integer postId, Integer depth);

    List<ReviewBoardCommentEntity> findByBundleIdAndDepth(Integer bundleId, Integer depth);


    void deleteByPostId(Integer postId);
}
