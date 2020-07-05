package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.LensEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LensRepository extends JpaRepository<LensEntity,Integer> {

}
