package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.LensEntity;
import com.springboot.intelllij.repository.FreeBoardRepository;
import com.springboot.intelllij.repository.LensRepository;
import com.springboot.intelllij.repository.UserRepository;
import com.springboot.intelllij.services.LensInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LensInfoController {

    @Autowired
    LensInfoService lensInfoService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @GetMapping("/api/lensinfo")
    public List<LensEntity> getLensInfo(){
        return lensInfoService.getAllLensInfo();
    }

    @GetMapping(value = "/api/lensinfo", params = {"id"})
    public LensEntity getLensInfoById(@RequestParam("id") Integer id) {
        return lensInfoService.getLensInfoById(id);
    }

    @GetMapping("/api/users")
    public Object getUsers() {
        Object result = userRepo.findAll();
        return result;
    }

    @GetMapping("/api/free-board")
    public Object getFreeBoard() {
        Object result = freeBoardRepository.findAll();
        return result;
    }

    @PostMapping(path = "/api/free-board", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        freeBoardRepository.save(freeBoard);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
