package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardPreviewRepository extends JpaRepository<FreeBoardViewEntity, Integer> {
}