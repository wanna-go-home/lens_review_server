package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.AccountBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBRepository extends JpaRepository<AccountBEntity, String> {
}
