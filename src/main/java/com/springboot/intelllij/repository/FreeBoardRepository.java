package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Integer> {
}
