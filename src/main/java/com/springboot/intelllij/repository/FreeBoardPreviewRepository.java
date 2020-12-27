package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.FreeBoardViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardPreviewRepository extends JpaRepository<FreeBoardViewEntity, Integer> {

    List<FreeBoardViewEntity> findByAccount(String account);
}
