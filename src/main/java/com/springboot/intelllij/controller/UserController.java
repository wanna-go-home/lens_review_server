package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.UserEntity;
import com.springboot.intelllij.repository.UserRepository;
import com.springboot.intelllij.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

}
