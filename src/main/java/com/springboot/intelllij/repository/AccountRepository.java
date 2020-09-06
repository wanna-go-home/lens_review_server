package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
}
