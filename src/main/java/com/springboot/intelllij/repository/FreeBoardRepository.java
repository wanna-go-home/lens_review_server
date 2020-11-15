package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Integer> {
    List<FreeBoardEntity> findByAccount(String account);
}
