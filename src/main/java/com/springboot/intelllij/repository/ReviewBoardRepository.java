package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoardEntity, Integer> {
}
