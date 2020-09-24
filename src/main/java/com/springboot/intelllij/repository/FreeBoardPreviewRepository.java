package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardPreviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardPreviewRepository extends JpaRepository<FreeBoardPreviewEntity, Integer> {
}
