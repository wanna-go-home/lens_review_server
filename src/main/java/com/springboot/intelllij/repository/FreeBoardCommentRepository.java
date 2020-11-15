package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardCommentEntity, Integer> {
    List<FreeBoardCommentEntity> findByPostId(Integer postId);

    List<FreeBoardCommentEntity> findByAccountId(String accountId);
}
