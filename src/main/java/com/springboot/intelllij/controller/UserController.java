package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RESTPath.USER)
public class UserController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/check/id")
    public ResponseEntity checkId(@RequestParam(value = "id") String id) {
        return accountService.checkId(id);
    }

    @GetMapping(value = "/check/nickname")
    public ResponseEntity checkNickName(@RequestParam(value = "nickname") String nickName) {
        return accountService.checkNickName(nickName);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody AccountEntity accountEntity) {
        return accountService.signup(accountEntity);
    }
}
