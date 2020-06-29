package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.OriginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OriginRepository extends JpaRepository<OriginEntity,Integer> {

}
