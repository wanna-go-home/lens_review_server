package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/api/accounts")
    public List<AccountEntity> getAllUsers() {
        return accountService.getAllUsers();
    }

}
