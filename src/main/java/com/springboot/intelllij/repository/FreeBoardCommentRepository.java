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

    List<FreeBoardCommentEntity> findByAccountId(Integer accountId);

    void deleteByPostId(Integer postId);

    @Modifying
    @Query("UPDATE FreeBoardCommentEntity e SET e.likeCnt = e.likeCnt + 1 WHERE e.id = :id")
    void increaseLikeCnt(@Param(value = "id") Integer id);

    @Modifying
    @Query("UPDATE FreeBoardCommentEntity e SET e.likeCnt = e.likeCnt - 1 WHERE e.id = :id")
    void decreaseLikeCnt(@Param(value = "id") Integer id);
}
