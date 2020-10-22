package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardPreviewRepository extends JpaRepository<ReviewBoardViewEntity, Integer> {
}
