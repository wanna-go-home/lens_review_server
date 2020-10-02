package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.LensPreviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LensPreviewRepository extends JpaRepository<LensPreviewEntity, Integer> {
}
