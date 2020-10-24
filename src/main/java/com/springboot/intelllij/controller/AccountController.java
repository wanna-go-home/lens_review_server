package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.ACCOUNTS)
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public List<AccountEntity> getAllUsers() {
        return accountService.getAllUsers();
    }

}
