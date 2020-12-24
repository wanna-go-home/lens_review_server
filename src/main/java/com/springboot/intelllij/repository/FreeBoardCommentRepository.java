package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardCommentEntity, Integer> {
    List<FreeBoardCommentEntity> findByPostId(Integer postId);

    List<FreeBoardCommentEntity> findByPostIdAndDepth(Integer postId, Integer depth);

    List<FreeBoardCommentEntity> findByBundleIdAndDepth(Integer bundleId, Integer depth);

    List<FreeBoardCommentEntity> findByEmail(String email);

    void deleteByPostId(Integer postId);
}
