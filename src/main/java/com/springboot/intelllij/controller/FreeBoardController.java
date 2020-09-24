package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.FreeBoardEntity;
import com.springboot.intelllij.domain.FreeBoardPreview;
import com.springboot.intelllij.services.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.FREE_BOARD)
public class FreeBoardController {

    @Autowired
    FreeBoardService freeBoardService;

    @GetMapping
    public List<FreeBoardEntity>  getFreeBoard() { return freeBoardService.getAllPosts(); }

    @GetMapping(RESTPath.FREE_BOARD_PREVIEW)
    public List<FreeBoardPreview>  getFreeBoardPreviews() { return freeBoardService.getAllPreviews(); }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addPostToFreeBoard(@RequestBody FreeBoardEntity freeBoard) {
        return freeBoardService.addPostToFreeBoard(freeBoard);
    }
}
