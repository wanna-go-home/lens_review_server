package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, String> {
}
