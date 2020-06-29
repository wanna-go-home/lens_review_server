package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ManufacturerEntity;
import com.springboot.intelllij.domain.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialEntity,Integer> {

}
