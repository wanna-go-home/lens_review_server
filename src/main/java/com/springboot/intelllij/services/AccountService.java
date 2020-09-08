package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository userRepo;

    public List<AccountEntity> getAllUsers() { return userRepo.findAll(); }

}
