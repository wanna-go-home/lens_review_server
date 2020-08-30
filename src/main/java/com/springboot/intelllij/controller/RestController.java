package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.repository.FreeBoardRepository;
import com.springboot.intelllij.repository.LensRepository;
import com.springboot.intelllij.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    LensRepository lensRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @GetMapping("/api/lensinfo")
    public Object getLensInfo() {
        Object result = lensRepo.findAll();
        return result;
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
