package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.FreeBoardCommentEntity;
import com.springboot.intelllij.domain.ReviewBoardCommentEntity;
import com.springboot.intelllij.domain.ReviewBoardEntity;
import com.springboot.intelllij.domain.ReviewBoardViewEntity;
import com.springboot.intelllij.repository.ReviewBoardCommentRepository;
import com.springboot.intelllij.services.ReviewBoardCommentService;
import com.springboot.intelllij.services.ReviewBoardPreviewService;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RESTPath.REVIEW_BOARD)
public class ReviewBoardController {

    @Autowired
    ReviewBoardService reviewBoardService;

    @Autowired
    ReviewBoardPreviewService reviewBoardPreviewService;

    @Autowired
    ReviewBoardCommentService reviewBoardCommentService;

    @GetMapping
    public List<ReviewBoardViewEntity> getReviewBoardPreview() { return reviewBoardPreviewService.getAllPreview(); }

    @GetMapping(value = "/{id}")
    public Optional<ReviewBoardEntity> getReviewBoardById(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.getReviewBoardById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardEntity reviewBoard) {
        return reviewBoardService.addPostToReviewBoard(reviewBoard);
    }

    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody ReviewBoardCommentEntity comment) {
        comment.setPostId(id);
        return reviewBoardCommentService.post(comment);
    }
}
