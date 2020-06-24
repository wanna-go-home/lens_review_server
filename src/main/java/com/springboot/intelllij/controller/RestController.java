package com.springboot.intelllij.controller;

import com.springboot.intelllij.repository.LensInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    LensInfoRepository lensRepo;

    @GetMapping("/api/lensinfo")
    public Object getLensInfo(){
        Object result = lensRepo.findAll();
        return result;
    }
}
