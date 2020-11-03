package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardCommentEntity, Integer> {
}
