package com.springboot.intelllij.services;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class FreeBoardService {

    @Autowired
    FreeBoardRepository freeBoardRepo;

    public List<FreeBoardEntity> getAllPosts() { return freeBoardRepo.findAll(); }

    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        freeBoardRepo.save(freeBoard);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
