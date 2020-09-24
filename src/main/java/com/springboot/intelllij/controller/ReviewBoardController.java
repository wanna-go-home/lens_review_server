package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardPreview;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RESTPath.REVIEW_BOARD)
public class ReviewBoardController {

    @Autowired
    ReviewBoardService reviewBoardService;

    @GetMapping
    public List<ReviewBoardEntity> getReviewBoard() { return reviewBoardService.getAllPosts(); }

    @GetMapping(RESTPath.REVIEW_BOARD_PREVIEW)
    public List<ReviewBoardPreview> getReviewBoardPreview() { return reviewBoardService.getAllPreviews(); }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardEntity reviewBoard) {
        return reviewBoardService.addPostToReviewBoard(reviewBoard);
    }
}
