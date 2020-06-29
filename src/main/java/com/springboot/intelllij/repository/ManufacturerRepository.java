package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity,Integer> {

}
