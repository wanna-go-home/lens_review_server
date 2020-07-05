package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity,Integer> {

}
