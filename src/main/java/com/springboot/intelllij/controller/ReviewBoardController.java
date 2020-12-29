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
    public ReviewBoardViewEntity getReviewBoardById(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.getReviewBoardById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReviewBoardById(
            @PathVariable(name = "id") Integer id, @RequestBody BoardUpdateDTO dto) {
        return reviewBoardService.updatePost(id, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletePostAndComments(@PathVariable(name = "id") Integer id) {
        return reviewBoardService.deletePostAndComments(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPostToReviewBoard(@RequestBody ReviewBoardDto reviewBoardDto) {
        return reviewBoardService.addPostToReviewBoard(reviewBoardDto);
    }

    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addCommentToFreeBoard(@PathVariable(name = "id") Integer id, @RequestBody CommentInputDTO comment) {
        return reviewBoardCommentService.post(id, comment);
    }

    @GetMapping(value = "/{id}/comments")
    public List<CommentOutputDTO> getReviewBoardComment(@PathVariable(name = "id") Integer postId) {
        return reviewBoardCommentService.getCommentByPostId(postId);
    }

    @GetMapping(value = "/{id}/comment/{commendId}")
    public List<CommentOutputDTO> getReviewBoardAllComments(@PathVariable(name = "id") Integer postId,
                                                                @PathVariable(name = "commendId") Integer commentId) {
        return reviewBoardCommentService.getAllCommentByPostId(postId,commentId);
    }

    @PutMapping(value = "/{id}/comment/{commendId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReviewBoardCommentEntity updateReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commendId") Integer commentId,
            @RequestBody String content) {
        return reviewBoardCommentService.updateComment(postId, commentId, content);
    }

    @DeleteMapping(value = "/{id}/comment/{commendId}")
    public ResponseEntity deleteReviewBoardCommentById(
            @PathVariable(name = "id") Integer postId,
            @PathVariable(name = "commendId") Integer commentId) {
        return reviewBoardCommentService.deleteComment(postId, commentId);
    }
  
    @GetMapping(value = "/me")
    public List<ReviewBoardViewEntity> getMyReviewBoardPreview() { return reviewBoardPreviewService.getMyAllPreview(); }

}
