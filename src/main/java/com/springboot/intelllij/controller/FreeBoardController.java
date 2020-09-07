package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.FreeBoardPreview;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FreeBoardController {

    @Autowired
    FreeBoardService freeBoardService;

    @GetMapping("/api/free-board")
    public List<FreeBoardEntity>  getFreeBoard() { return freeBoardService.getAllPosts(); }

    @GetMapping("/api/free-board/preview")
    public List<FreeBoardPreview>  getFreeBoardPreviews() { return freeBoardService.getAllPreviews(); }

    @PostMapping(path = "/api/free-board", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }
}
