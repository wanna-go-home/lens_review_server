package com.springboot.intelllij.controller;

import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardPreview;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewBoardController {

    @Autowired
    ReviewBoardService reviewBoardService;

    @GetMapping("/api/review-board")
    public List<ReviewBoardEntity> getReviewBoard() { return reviewBoardService.getAllPosts(); }

    @GetMapping("/api/review-board/preview")
    public List<ReviewBoardPreview> getReviewBoardPreview() { return reviewBoardService.getAllPreviews(); }

    @PostMapping(path = "/api/review-board", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardEntity reviewBoard) {
        return reviewBoardService.addPostToReviewBoard(reviewBoard);
    }
}
