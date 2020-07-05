package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.LensImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LensImageRepository extends JpaRepository<LensImageEntity,Integer> {

}
