package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBoardPreviewRepository extends JpaRepository<ReviewBoardViewEntity, Integer> {

    List<ReviewBoardViewEntity> findByAccountId(Integer AccountId);
}
