package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.UserEntity;
import com.springboot.intelllij.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public List<UserEntity> getAllUsers() { return userRepo.findAll(); }

}
