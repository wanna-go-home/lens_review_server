package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FreeBoardController {

    @Autowired
    FreeBoardService freeBoardService;

    @GetMapping("/api/free-board")
    public List<FreeBoardEntity>  getFreeBoard() { return freeBoardService.getAllPosts(); }

    @PostMapping(path = "/api/free-board", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }
}
