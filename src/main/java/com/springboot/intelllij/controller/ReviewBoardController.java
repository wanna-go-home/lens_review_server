package com.springboot.intelllij.controller;

import com.springboot.intelllij.constant.RESTPath;
import com.springboot.intelllij.domain.*;
import com.springboot.intelllij.services.ReviewBoardCommentService;
import com.springboot.intelllij.services.ReviewBoardPreviewService;
import com.springboot.intelllij.services.ReviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.intelllij.constant.RESTPath.*;

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

    @GetMapping(value = POST_ID)
    public ReviewBoardViewEntity getReviewBoardById(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.getReviewBoardById(id);
    }

    @PutMapping(value = POST_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReviewBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        return reviewBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = POST_ID)
    public ResponseEntity deletePostAndComments(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardDto reviewBoardDto) {
        return reviewBoardService.addPostToReviewBoard(reviewBoardDto);
    }

    @PostMapping(value = POST_COMMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody ReviewBoardCommentEntity comment) {
        comment.setPostId(id);
        return reviewBoardCommentService.post(comment);
    }

    @GetMapping(value = POST_COMMENTS)
    public List<ReviewBoardCommentEntity> getReviewBoardComment(@PathVariable(name = "id") Integer postId) {
        return reviewBoardCommentService.getCommentByPostId(postId);
    }

//    @GetMapping(value = POST_AND_COMMENT_ID)
//    public List<ReviewBoardCommentEntity> getReviewBoardA

    @PutMapping(value = POST_AND_COMMENT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReviewBoardCommentEntity updateReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId,
            @RequestBody String content) {
        return reviewBoardCommentService.updateComment(postId, commentId, content);
    }

    @DeleteMapping(value = POST_AND_COMMENT_ID)
    public ResponseEntity deleteReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commentId") Integer commentId) {
        return reviewBoardCommentService.deleteComment(postId, commentId);
    }
}
