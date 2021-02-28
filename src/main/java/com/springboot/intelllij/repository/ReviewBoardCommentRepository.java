package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewBoardCommentRepository extends JpaRepository<ReviewBoardCommentEntity, Integer> {
    List<ReviewBoardCommentEntity> findByPostId(Integer postId);

    List<ReviewBoardCommentEntity> findByAccountId(Integer accountId);

    List<ReviewBoardCommentEntity> findByPostIdAndDepth(Integer postId, Integer depth);

    List<ReviewBoardCommentEntity> findByBundleIdAndDepth(Integer bundleId, Integer depth);

    List<ReviewBoardCommentEntity> findByBundleIdAndDepthOrderByCreatedAtAsc(Integer bundleId, Integer depth);

    void deleteByPostId(Integer postId);
}
