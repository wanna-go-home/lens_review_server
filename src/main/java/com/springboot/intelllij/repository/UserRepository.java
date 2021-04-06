package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.domain.NewAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<NewAccountEntity, Integer> {

    List<NewAccountEntity> findByPhoneNum(String phoneNum);
}
