package com.springboot.intelllij.repository;

import com.springboot.intelllij.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    List<AccountEntity> findByNickname(String nickname);

    List<AccountEntity> findByPhoneNum(String phoneNum);
}
