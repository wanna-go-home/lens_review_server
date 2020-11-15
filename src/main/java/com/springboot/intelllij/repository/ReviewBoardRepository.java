package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ReviewBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoardEntity, Integer> {
    List<ReviewBoardEntity> findByAccount(String account);
}
