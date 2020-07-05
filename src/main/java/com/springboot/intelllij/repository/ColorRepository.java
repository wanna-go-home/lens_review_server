package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorEntity,Integer> {

}
