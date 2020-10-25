package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository userRepo;

    public List<AccountEntity> getAllUsers() { return userRepo.findAll(); }

    public ResponseEntity checkId(String id) {
        if (userRepo.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity checkNickName(String nickName) {
        List<AccountEntity> nickNameList = userRepo.findByNickname(nickName);
        if(nickNameList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public AccountEntity signup(String id, String pw, String email, String nickName, String phoneNumber) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AccountEntity newUser = new AccountEntity(id,bCryptPasswordEncoder.encode(pw),nickName,email,phoneNumber);
        return userRepo.save(newUser);
    }

}
