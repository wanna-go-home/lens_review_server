package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardPreviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardPreviewRepository extends JpaRepository<ReviewBoardPreviewEntity, Integer> {
}
