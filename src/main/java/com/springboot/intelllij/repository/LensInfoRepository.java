package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.LensInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LensInfoRepository extends JpaRepository<LensInfoEntity,Integer> {

}
