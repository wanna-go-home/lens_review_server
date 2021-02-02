package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardCommentEntity, Integer> {
    List<FreeBoardCommentEntity> findByPostId(Integer postId);

    List<FreeBoardCommentEntity> findByPostIdAndDepth(Integer postId, Integer depth);

    List<FreeBoardCommentEntity> findByBundleIdAndDepth(Integer bundleId, Integer depth);

    List<FreeBoardCommentEntity> findByBundleIdAndDepthOrderByCreatedAtAsc(Integer bundleId, Integer depth);

    List<FreeBoardCommentEntity> findByAccountId(Integer accountId);

    void deleteByPostId(Integer postId);
}
