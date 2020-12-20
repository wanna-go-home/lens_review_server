package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.AccountEntity;
import com.springboot.intelllij.domain.UserInfoDTO;
import com.springboot.intelllij.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.USER)
public class UserController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public List<AccountEntity> getAllUsers() {
        return accountService.getAllUsers();
    }

    @DeleteMapping
    public ResponseEntity deleteUser() {
        return accountService.deleteUser();
    }


    @GetMapping(value = "/check/id")
    public ResponseEntity checkId(@RequestParam(value = "id") String id) {
        return accountService.checkId(id);
    }

    @GetMapping(value = "/check/nickname")
    public ResponseEntity checkNickName(@RequestParam(value = "nickname") String nickName) {
        return accountService.checkNickName(nickName);
    }

    @GetMapping(value = "/check/phoneNum")
    public ResponseEntity checkPhoneNumber(@RequestParam(value = "phoneNum") String phoneNum) {
        return accountService.checkPhoneNumber(phoneNum);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody AccountEntity accountEntity) {
        return accountService.signup(accountEntity);
    }

    @GetMapping(value = "/me")
    public UserInfoDTO userInfo() {
        return accountService.getUserInfo();
    }
}
