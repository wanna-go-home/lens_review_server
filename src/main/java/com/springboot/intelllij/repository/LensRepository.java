package com.springboot.intelllij.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.intelllij.domain.LensEntity;

public interface LensRepository extends JpaRepository<LensEntity,Integer> {

}
