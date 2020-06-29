package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.PWREntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PWRRepository extends JpaRepository<PWREntity,Integer> {

}
