package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByAccountEmail(String email);

    List<AccountEntity> findByNickname(String nickname);

    List<AccountEntity> findByPhoneNum(String phoneNum);
}
